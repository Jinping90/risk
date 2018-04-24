package com.wjl.fcity.welfare.controller;

import com.google.common.collect.Maps;
import com.wjl.fcity.welfare.common.enums.CodeEnum;
import com.wjl.fcity.welfare.dto.*;
import com.wjl.fcity.welfare.model.User;
import com.wjl.fcity.welfare.model.WealthRecord;
import com.wjl.fcity.welfare.service.UserService;
import com.wjl.fcity.welfare.service.WelfareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 福利社区模块
 *
 * @author : Fy
 * @date : 2018-03-27 10:15
 */
@Slf4j
@RestController
@RequestMapping("/welfare/auth")
public class WelfareController {

    @Resource
    private WelfareService welfareService;

    @Resource
    private UserService userService;

    /**
     * 用户签到
     *
     * @param request 用户的userId
     * @return Response
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public Response signIn(HttpServletRequest request) {

        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【用户签到】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        Long userId = Long.valueOf(userIdStr);
        User user = userService.findOne(userId);

        if (null == user) {
            log.error("【用户签到】用户userId={},用户不存在！", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "用户未注册");
        }

        log.info("【用户签到】用户的userId={},开始处理业务逻辑", userId);

        Integer addCreditValue = welfareService.signIn(userId);
        if (null == addCreditValue) {
            log.error("【用户签到】用户的userId={},已经签到，请勿重复签到", userId);
            return new Response<>(CodeEnum.AUTH_REPEAT_SIGN, "用户已签到，请勿重复签到");
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("addCreditValue", addCreditValue);

        return new Response<>(CodeEnum.SUCCESS, map);
    }


    /**
     * 收取货币的接口
     *
     * @param request   用户的userId
     * @param gatherDto welfareId 财富id
     * @return Response
     */
    @RequestMapping(value = "/gather", method = RequestMethod.POST)
    public Response gather(HttpServletRequest request, @RequestBody GatherDto gatherDto) {

        String welfareId = gatherDto.getWelfareId();
        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【收取货币】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        if (StringUtils.isEmpty(welfareId)) {
            log.error("【收取货币】参数不正确，用户的welfareId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的welfareId为空!");
        }

        Long userId = Long.valueOf(userIdStr);
        User user = userService.findOne(userId);

        if (null == user) {
            log.error("【收取货币】用户userId={},用户不存在！", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "用户未注册!");
        }

        boolean isTrue = welfareService.gather(userId, Long.valueOf(welfareId));
        if (isTrue) {
            log.info("【收取货币】用户userId={},货币收取成功！", userId);
            return new Response<>(CodeEnum.SUCCESS, "货币收取成功!");
        } else {
            log.info("【收取货币】用户userId={},货币收取失败！", userId);
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "财富记录或是金币状态不正确货币收取失败!");
        }
    }

    /**
     * 查询用户货币展示和历史记录
     *
     * @param request   用户的userId
     * @param recordDto status  状态[0: 未收取, 1: 已收取, 2: 已过期]
     *                  page    当前页
     *                  size    显示条数
     * @return Response
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public Response findRecord(HttpServletRequest request, @RequestBody RecordDto recordDto) {
        String page = recordDto.getPage();
        String size = recordDto.getSize();
        String status = recordDto.getStatus();

        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【货币历史记录和展示】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        Long userId = Long.valueOf(userIdStr);
        User user = userService.findOne(userId);

        if (null == user) {
            log.error("【货币历史记录和展示】用户userId={},用户不存在！", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "用户的userId为空!");
        }

        if (StringUtils.isEmpty(status)) {
            log.error("【货币历史记录和展示】参数不正确，用户userId={}的status状态不能为空!", userId);
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        if (StringUtils.isEmpty(page)) {
            //默认未第一页
            Integer defaultPage = 1;
            page = String.valueOf(defaultPage);
        }

        if (StringUtils.isEmpty(size)) {
            //默认显示20条
            Integer defaultSize = 20;
            size = String.valueOf(defaultSize);
        }

        //获取未收取 将该用户大于72小时的 更新为已过期
        welfareService.updateOverdueWealth(userId);

        //查询该用户( 状态[0: 未收取, 1: 已收取, 2: 已过期])虚拟币的列表
        log.info("【货币历史记录和展示】查询该用户userId={}，在状态status={}的虚拟币的集合！", userId, status);
        List<WealthRecord> allGatherWealthList = welfareService.getAllGatherWealth(userId, Integer.valueOf(status), page, size);

        //查询在该状态下的总数
        int wealthNum = welfareService.getAllGatherWealthNum(userId, Integer.valueOf(status));
        log.info("【货币历史记录和展示】查询该用户userId={}，在状态status={}的记录条数为wealthNum={}！", userId, status, wealthNum);

        //用户总的财富值
        BigDecimal totalWealth = userService.findOne(userId).getTotalWealth();
        String totalWealthStr = totalWealth == null ? BigDecimal.ZERO.toString() : totalWealth.toString();

        //属性拷贝
        List<WealthRecordDto> wealthRecordDtoList = null;
        if (!CollectionUtils.isEmpty(allGatherWealthList)) {
            wealthRecordDtoList = allGatherWealthList
                    .stream()
                    .map(e -> new WealthRecordDto(e.getChangeWealth().toString(), e.getGmtModified(), e.getId()))
                    .collect(Collectors.toList());
        }

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("list", wealthRecordDtoList);
        map.put("total", wealthNum);
        map.put("size", size);
        map.put("page", page);
        map.put("totalWealth", totalWealthStr);

        return new Response<>(CodeEnum.SUCCESS, map);
    }


    /**
     * 获取用户的全服或是个人排行榜
     *
     * @param request 用户的userId
     * @param rankDto size    全服排行榜（前20，或是30）由前端传入
     *                type    0:表示全服排行 1：个人用户信用值排行（size为空）
     * @return Response
     */
    @RequestMapping(value = "fullService/Ranking", method = RequestMethod.POST)
    public Response getFullServiceRanking(@RequestBody RankDto rankDto, HttpServletRequest request) {

        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【全服或是个人排行榜】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        if (StringUtils.isBlank(rankDto.getType())) {
            log.error("【全服或是个人排行榜】参数输入不正确,type为空");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的type为空!");
        }

        if (StringUtils.isBlank(rankDto.getSize())) {
            log.error("【全服或是个人排行榜】参数输入不正确,size为空");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户size为空!");
        }

        Long userId = Long.valueOf(userIdStr);
        User user = userService.findOne(userId);

        if (null == user) {
            log.error("【全服或是个人排行榜】用户userId={},用户不存在！", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "用户的userId为空!");
        }

        Map<String, Object> map = welfareService.getAllServiceOrOwnRanking(user, rankDto.getType(), rankDto.getSize());
        return new Response<>(CodeEnum.SUCCESS, map);
    }


}
