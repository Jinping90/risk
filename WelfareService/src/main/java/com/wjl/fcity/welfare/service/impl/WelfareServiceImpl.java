package com.wjl.fcity.welfare.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.wjl.fcity.welfare.common.enums.CreditValueRecordEnum;
import com.wjl.fcity.welfare.common.enums.CreditValueUserEnum;
import com.wjl.fcity.welfare.common.enums.WealthRecordStatusEnum;
import com.wjl.fcity.welfare.common.util.SignCreditValue;
import com.wjl.fcity.welfare.dto.RankingDto;
import com.wjl.fcity.welfare.mapper.WelfareMapper;
import com.wjl.fcity.welfare.model.CreditValueRecord;
import com.wjl.fcity.welfare.model.SignIn;
import com.wjl.fcity.welfare.model.User;
import com.wjl.fcity.welfare.model.WealthRecord;
import com.wjl.fcity.welfare.service.UserService;
import com.wjl.fcity.welfare.service.WelfareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author : Fy
 * @implSpec : 福利社区模块
 * @date : 2018-03-27 10:30
 */
@Service
@Slf4j
public class WelfareServiceImpl implements WelfareService {

    @Resource
    private WelfareMapper welfareMapper;

    @Resource
    private UserService userService;

    /**
     * 保存用户连续签到的天数，当天签到返回信用值
     *
     * @param userId 用户的userId
     * @return 签到后随机永久增加的信用值（0~3之间）
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer signIn(Long userId) {

        SignIn signIn = new SignIn();
        signIn.setUserId(userId);
        signIn.setGmtCreated(new Date());

        log.info("【用户签到】判断用户当天userId={}是否签到过！", userId);
        Integer signCount = welfareMapper.countByTime(userId, getStartTime(0), getEndTime(0));

        Integer addCreditValue;
        if (0 == signCount) {
            signIn.setGmtModified(new Date());
            welfareMapper.insertSignIn(signIn);

///            Random random = new Random();
///            //生成(1~3)随机数
///            addCreditValue = random.nextInt(3) + 1;
///            log.info("【用户签到】用户userId={}签到，将增加addCreditValue={}点信用值", userId, addCreditValue);

            //将用户其签到的信用值，永久的增加到user的credit_value字段中
            User user = userService.findOne(userId);
            int oldCreditValue = user.getCreditValue();
            //根据用户等级来产生签到的信用值
            String userCreditValue;

            if (oldCreditValue < CreditValueUserEnum.COPPER_USER.getScore()) {
                //普通用户﹤200分
                userCreditValue = welfareMapper.getUserGradeAward(CreditValueUserEnum.ORDINARY_USER.getMsg());

            } else if (CreditValueUserEnum.COPPER_USER.getScore() <= oldCreditValue && oldCreditValue < CreditValueUserEnum.SILVER_USER.getScore()) {
                //铜级用户:200分≤铜﹤500分；
                userCreditValue = welfareMapper.getUserGradeAward(CreditValueUserEnum.COPPER_USER.getMsg());

            } else {
                //银级以上用户:500分≤银﹤600分；600分≤金
                userCreditValue = welfareMapper.getUserGradeAward(CreditValueUserEnum.SILVER_USER.getMsg());

            }

            Map userCreditValueMap = JSONObject.parseObject(userCreditValue, Map.class);

            //调用工具类来随机生成今日签到产生的信用值
            addCreditValue = SignCreditValue.getSignUserCreditValue(userCreditValueMap);

            user.setCreditValue(addCreditValue + oldCreditValue);
            user.setGmtModified(new Date());

            Integer signNum = user.getSignInDays();
            Integer signInDays = signNum == null ? 0 : signNum;

            //查询昨天用户是否签到，判断签到是否中断
            int yesterdaySign = welfareMapper.countByTime(userId, getStartTime(-1), getEndTime(-1));
            if (0 != yesterdaySign) {
                signInDays = signInDays + 1;
            } else {
                signInDays = 1;
            }
            user.setSignInDays(signInDays);

            //更新新用户表中的信用值和连续签到天数
            userService.updateUser(user);

            //向用户信用值记录表(credit_value_record)中插入记录
            log.info("【用户签到】用户userId={}签到，增加addCreditValue={}点信用值,保存用户信用值记录表中", userId, addCreditValue);
            CreditValueRecord creditValueRecord = new CreditValueRecord();
            creditValueRecord.setUserId(userId);
            creditValueRecord.setChangeCreditValue(addCreditValue);
            creditValueRecord.setType(CreditValueRecordEnum.SIGN.getStatus());
            creditValueRecord.setGmtCreated(new Date());
            creditValueRecord.setGmtModified(new Date());
            welfareMapper.insertCreditValueRecordToSign(creditValueRecord);

            log.info("【用户签到】用户userId={}签到，增加addCreditValue={}点信用值,保存成功", userId, addCreditValue);
            return addCreditValue;
        }
        return null;
    }

    /**
     * 收取货币
     *
     * @param userId    用户的userId
     * @param welfareId 收取的该货币的id
     * @return boolean[是否收取成功]
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean gather(Long userId, Long welfareId) {

        log.info("【收取货币】用户userId={}，开始收取货币，处理业务逻辑", userId);

        WealthRecord wealthRecord = new WealthRecord();
        wealthRecord.setStatus(WealthRecordStatusEnum.UN_GATHER.getStatus());
        wealthRecord.setUserId(userId);
        wealthRecord.setId(welfareId);

        log.info("【收取货币】用户获取用户userId={},财富记录表id={}未获取的财富值", userId, welfareId);
        WealthRecord oldWealthRecord = welfareMapper.notGetGather(wealthRecord);

        if (null == oldWealthRecord) {
            log.error("【收取货币】用户获取用户userId={},财富记录id={}的财富值不存在", userId, welfareId);
            return false;

        } else if (!WealthRecordStatusEnum.UN_GATHER.getStatus().equals(oldWealthRecord.getStatus())) {
            log.error("【收取货币】用户获取用户userId={},财富记录id={}的状态不正确", userId, welfareId);
            return false;

        } else {
            //更改财富记录表的状态
            oldWealthRecord.setStatus(WealthRecordStatusEnum.GATHERED.getStatus());
            oldWealthRecord.setGmtModified(new Date());
            welfareMapper.updateOldWealthRecord(oldWealthRecord);

            //更新用户总的财富值
            userService.updateTotalWealth(oldWealthRecord.getChangeWealth(), userId);
            return true;
        }
    }

    /**
     * 更新该用户72小时内未收取的财富为过期
     *
     * @param userId 该用户的userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOverdueWealth(Long userId) {
        //得到三天前的精确时间
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        long time = calendar.getTime().getTime() / (1000);

        welfareMapper.updateOverdueWealth(userId, WealthRecordStatusEnum.OVERDUE.getStatus(), time);
        log.info("【货币历史记录和展示】用户userId={}72小时内未收取的财富值已更新未过期", userId);
    }

    /**
     * 获取该用户在该状态下的财富值集合
     *
     * @param userId 用户的UserId
     * @return 获取该用户在该状态下的财富值集合
     */
    @Override
    public List<WealthRecord> getAllGatherWealth(Long userId, Integer status, String page, String size) {

        log.info("【货币历史记录和展示】获取用户userId={},虚拟币状态为status={}所有财富的集合", userId, status);

        Integer pageInteger = (Integer.valueOf(page) - 1) * (Integer.valueOf(size));
        Integer pageSize = Integer.valueOf(size);
        return welfareMapper.getAllNotGatherWealth(userId, status, pageInteger, pageSize);
    }

    /**
     * 查询该用户在该状态status的总数
     *
     * @param userId 用户的userId
     * @param status 状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @return 统计总数
     */
    @Override
    public int getAllGatherWealthNum(Long userId, Integer status) {
        return welfareMapper.getAllGatherWealthNum(userId, status);
    }

    /**
     * 获取全服或是个人的排行榜（信用值）
     *
     * @param user 用户的对象
     * @param type 0:表示全服排行 1：个人用户算力排行（size为空）
     * @param size 全服前多少名的排行
     */
    @Override
    public Map<String, Object> getAllServiceOrOwnRanking(User user, String type, String size) {

        log.info("【全服或是个人排行榜】用户的userId={},开始获取全服用户的集合", user.getId());

        List<RankingDto> allServiceOrOwnRankingDescList = userService.getAllServiceOrOwnRankingDesc();

        //该用户的信用值
        Integer ownCreditValue = user.getCreditValue();
        ownCreditValue = user.getCreditValue() == null ? Integer.valueOf(0) : ownCreditValue;
        log.info("【全服或是个人排行榜】用户的userId={},信用值为ownCreditValue={}", ownCreditValue);

        //该用户的财富值
        BigDecimal userTotalWealth = user.getTotalWealth();
        String ownTotalWealth = userTotalWealth == null ? BigDecimal.ZERO.toString() : userTotalWealth.toString();
        log.info("【全服或是个人排行榜】用户的userId={},ownTotalWealth={}", ownTotalWealth);

        //所有用户名脱敏操作
        allServiceOrOwnRankingDescList
                .forEach((RankingDto userPep)
                        -> userPep.setUsername(chineseName(userPep.getUsername())));

        //0:表示全服排行 1：个人用户算力排行 全服前多少名的排行
        String allServiceLevel = String.valueOf(0);
        List<RankingDto> rankingDtoList = null;
        if (type.equals(allServiceLevel) && !CollectionUtils.isEmpty(allServiceOrOwnRankingDescList)) {

            log.info("【全服或是个人排行榜】计算全服前size={}的排行榜", size);
            rankingDtoList = allServiceOrOwnRankingDescList
                    .stream()
                    .limit(Integer.valueOf(size))
                    .collect(Collectors.toList());
        }

        int ownCountRanking = 0;
        for (RankingDto serviceOrOwnList : allServiceOrOwnRankingDescList) {
            ownCountRanking++;
            if (serviceOrOwnList.getUserId().equals(user.getId())) {
                break;
            }
        }

        //得到该用户本区的排行 定义过滤器，然后重用他们来执行其他操作 定义 filers
///        Integer finalOwnCreditValue = ownCreditValue;
///        Predicate<RankingDto> ageFilter = userFilter
///                -> (finalOwnCreditValue - userFilter.getCreditValue() <= 0);

        //Math.toIntExact()无溢出计算：如果溢出则抛异常 Integer.MAX_VALUE = 2的32次 -1
///        Integer ownCountRanking = Math.toIntExact(allServiceOrOwnRankingDescList
///                .stream()
///                .filter(ageFilter)
///                .count());
        log.info("【全服或是个人排行榜】用户userId={}全服的排行为ownCountRanking={}", ownCountRanking);

        Map<String, Object> map = Maps.newHashMap();
        map.put("list", rankingDtoList);
        map.put("ownName", user.getUsername());
        map.put("ownTotalWealth", ownTotalWealth);
        map.put("ownCreditValue", ownCreditValue);
        map.put("ranking", ownCountRanking);

        return map;
    }

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     *
     * @param fullName 用户的全称名字
     * @return 脱敏后的名字
     */
    private static String chineseName(String fullName) {

        if (StringUtils.isBlank(fullName)) {
            return null;
        }

        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * 签到开始时间
     *
     * @param day o表示当天的时间，依次类推
     * @return 当天凌晨的毫秒数
     */
    private static Long getStartTime(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime() / 1000;
    }

    /**
     * 签到结束时间
     *
     * @param day o表示当天的时间，依次类推
     * @return 当天结束时间的最后一秒的毫秒数
     */
    private static Long getEndTime(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime().getTime() / (1000);
    }


}
