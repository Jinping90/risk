package com.wjl.fcity.coretask.service.impl;

import com.google.common.collect.Maps;
import com.wjl.fcity.coretask.common.config.WealthConfig;
import com.wjl.fcity.coretask.common.util.BigDecimalUtil;
import com.wjl.fcity.coretask.common.util.DateUtil;
import com.wjl.fcity.coretask.mapper.ConfigGenerateWealthMapper;
import com.wjl.fcity.coretask.mapper.CreditValueRecordMapper;
import com.wjl.fcity.coretask.mapper.UserMapper;
import com.wjl.fcity.coretask.model.ConfigGenerateWealthDO;
import com.wjl.fcity.coretask.model.WealthRecordDO;
import com.wjl.fcity.coretask.service.WealthRecordService;
import com.wjl.fcity.coretask.service.WealthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.WebXml;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 财富值业务处理实现
 *
 * @author czl
 */
@Slf4j
@Service
public class WealthServiceImpl implements WealthService {

    @Resource
    private WealthRecordService wealthRecordService;
    @Resource
    private ConfigGenerateWealthMapper configGenerateWealthMapper;
    @Resource
    private WealthConfig wealthConfig;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CreditValueRecordMapper creditValueRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wealthComputeEveryDay() {
        int generateIntervalHours = wealthConfig.getGenerateIntervalHours();
        //定义总的算力值
        double totalPowerValue = 0D;
        Date nowDate = new Date();

        //generateIntervalHours小时前的时间
        Date generateIntervalHoursAge = DateUtil.getHourAgo(System.currentTimeMillis(), generateIntervalHours);

        //定义保存userId和算力值的 map
        Map<Long, Double> userMap = Maps.newHashMap();
        //查询可参与结算的用户
        List<Long> userIdList = userMapper.listLessThanDate(generateIntervalHoursAge);
        if (null == userIdList || userIdList.size() == 0) {
            log.info("当前没有可以结算的用户");
            return;
        }
        for (Long userId : userIdList) {
            long startTime = System.currentTimeMillis();
            log.info(String.format("用户userId=%s,开始时间=%s", userId, startTime));
            Double coefficient = 1d;
            log.info(String.format("用户user=%s,算力系数coefficient=%s", userId, coefficient));
            //综合分数
            double compositeScore = getCompositeScore(userId, generateIntervalHoursAge);
            log.info(String.format("用户user=%s,综合算力compositeScore=%s", userId, compositeScore));
            //基础加提升算力
            double powerScore = getPowerScore(userId, generateIntervalHoursAge);
            log.info(String.format("用户user=%s,基础加提升算力powerScore=%s", userId, powerScore));
            //(基础算力+提升算力)*算力系数+算力综合调整
            Double hashRate = powerScore * coefficient + compositeScore;

            Double finHashRate = Math.min(Math.max(hashRate, 20d), 800d);
            log.info(String.format("用户user=%s,最终算力finHashRate=%s", userId, finHashRate));

            userMap.put(userId, finHashRate);

            totalPowerValue += finHashRate;
            long endTime = System.currentTimeMillis();
            log.info(String.format("用户userId=%s,结束时间=%s，用时=%s", userId, endTime, endTime - startTime));
        }

        //获取当前时间2小时前的时间,涉及到分月，减3小时
        Date computeDate = DateUtil.rollHour(nowDate, -generateIntervalHours - 1);
        //获取当前年和月
        int computeYear = DateUtil.getTimeYear(computeDate);
        int computeMonth = DateUtil.getTimeMonth(computeDate);
        ConfigGenerateWealthDO configGenerateWealthDO = configGenerateWealthMapper.getByPutYearAndPutMonth(computeYear, computeMonth);

        //每日  每次放qtb数量
        double qtbCount = (configGenerateWealthDO.getDayWealthCount().doubleValue()) * generateIntervalHours / 24d;

        log.info(String.format("2小时前时间%s,2小时前年份%s,2小时前月份%s，7天前单日放币数%s", computeDate.toString(), computeYear, computeMonth, qtbCount));
        log.info("所有用户的算力和为:" + totalPowerValue);

        //遍历所有的user，在QBT记录表中插入数据
        for (Long key : userMap.keySet()) {
            //计算QBT值
            Double value = userMap.get(key);
            BigDecimal powerValue = new BigDecimal(value);
            BigDecimal changeQbt = new BigDecimal(BigDecimalUtil.round(value / totalPowerValue * qtbCount, 4));
            Integer status = 0;
            Integer type = 1;

            WealthRecordDO wealthRecordDO = new WealthRecordDO();
            wealthRecordDO.setUserId(key);
            wealthRecordDO.setChangeWealth(changeQbt);
            wealthRecordDO.setStatus(status);
            wealthRecordDO.setType(type);
            wealthRecordDO.setGenerateCreditValue(powerValue);

            wealthRecordService.insert(wealthRecordDO);
        }
    }

    /**
     * 获取调整算力（N天前，定时器用）
     *
     * @param userId 用户ID
     * @param date   时间
     * @return 综合分数
     */
    private double getCompositeScore(Long userId, Date date) {
        double score = 0D;

        //用户在某时间前的其他的总算力
        Integer compositeScore = creditValueRecordMapper.getOtherCreditValue(userId, date);

        if (compositeScore != null) {
            score = compositeScore.doubleValue();
        }
        return score;
    }

    /**
     * 计算：基础算力+提升算力(N天前，定时器用)
     *
     * @return 算力
     */
    private double getPowerScore(Long userId, Date date) {
        double score = 0D;

        //用户在某时间前的认证的总算力
        Integer compositeScore = creditValueRecordMapper.getAuthCreditValue(userId, date);

        if (compositeScore != null) {
            score = compositeScore.doubleValue();
        }
        return score;
    }
}
