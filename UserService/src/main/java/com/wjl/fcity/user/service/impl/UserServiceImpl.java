package com.wjl.fcity.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wjl.fcity.user.LocalCache;
import com.wjl.fcity.user.common.enums.AuthCategoryEnum;
import com.wjl.fcity.user.common.enums.AuthItemEnum;
import com.wjl.fcity.user.common.enums.AuthStatusEnum;
import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.common.properties.AppProperties;
import com.wjl.fcity.user.common.util.AesUtils;
import com.wjl.fcity.user.common.util.DateUtil;
import com.wjl.fcity.user.common.util.PasswordUtil;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.dto.UserAuthReportDTO;
import com.wjl.fcity.user.mapper.ConfigAuthParamMapper;
import com.wjl.fcity.user.mapper.PromotionUserMapper;
import com.wjl.fcity.user.mapper.UserAuthRecordMapper;
import com.wjl.fcity.user.mapper.UserMapper;
import com.wjl.fcity.user.model.*;
import com.wjl.fcity.user.model.constant.RedisKeyConstant;
import com.wjl.fcity.user.model.vo.CityInfoVO;
import com.wjl.fcity.user.model.vo.UserAuthReportVO;
import com.wjl.fcity.user.model.vo.UserInfoVO;
import com.wjl.fcity.user.po.CityConfigPO;
import com.wjl.fcity.user.po.LoginRecordPO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.request.UserReq;
import com.wjl.fcity.user.service.CreditValueRecordService;
import com.wjl.fcity.user.service.LoginRecordService;
import com.wjl.fcity.user.service.RedisService;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author shengju
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AppProperties appProperties;
    @Resource
    private LoginRecordService loginRecordService;
    @Resource
    private RedisService redisService;
    @Resource
    private ConfigAuthParamMapper configAuthParamMapper;
    @Resource
    private UserAuthRecordMapper userAuthRecordMapper;
    @Resource
    private CreditValueRecordService creditValueRecordService;
    @Resource
    private PromotionUserMapper promotionUserMapper;


    /**
     * 注册发送验证码的类型
     */
    @Value("${vCode.register}")
    private Integer register;

    /**
     * 重置密码
     */
    @Value("${vCode.resetPwd}")
    private Integer resetPwd;

    /**
     * 登录
     */
    @Value("${vCode.login}")
    private Integer login;

    @Value("${vCode.findPwd}")
    private Integer findPwd;

    /**
     * 根据用户的userId，获取用户的信息
     *
     * @param userId 用户ID
     * @return 用户表的信息
     */
    @Override
    public UserPO findOne(Long userId) {
        return userMapper.findOne(userId);
    }

    @Override
    public UserPO findByMobile(String mobile) {
        return userMapper.findUserByMobile(mobile);
    }


    @Override
    public Map login(UserReq userReq, String ip) {

        UserPO userPO = userMapper.findUserByMobile(userReq.getMobile());
        if (null == userPO) {
            log.info(String.format("用户%s不存在，登录失败，登录结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_NO_USER);
        }
        if (LocalCache.hasMoreLoginFailRequest(userPO.getUsername())) {
            log.error(String.format("用户mobile=%s，连续登录失败3次以上", userPO.getMobile()));
            throw new BaseException(CodeEnum.AUTH_LOGIN_COUNT);
        }
        try {
            if (StringUtil.isBlank(userPO.getPassword())) {
                // 用户未设置密码
                log.info("用户未设置密码:%s", userPO.getMobile());
                throw new BaseException(CodeEnum.AUTH_NO_SET);
            }
            if (!PasswordUtil.validatePassword(userPO.getPassword(), userReq.getPassword())) {
                log.info(String.format("用户mobile=%s输入密码不正确，登录失败，登录结束", userReq.getMobile()));
                LocalCache.hasMoreLoginFailRequest(userPO.getUsername());
                throw new BaseException(CodeEnum.AUTH_PWD_ERR);
            }
        } catch (BaseException be) {
            throw new BaseException(CodeEnum.AUTH_PWD_ERR);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.info("验证密码失败，原因：" + e);
            log.info(String.format("用户mobile=%s密码加密失败，登录失败，登录结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_PWD_ERR);
        }
        Long nowTime = System.currentTimeMillis();
        Date now = new Date();
        String token;
        try {
            Token userToken = new Token();
            userToken.setLoginTime(nowTime);
            userToken.setMobile(userPO.getMobile());
            userToken.setId(userPO.getId());

            token = AesUtils.aesEncryptHexString(JSON.toJSONString(userToken), appProperties.getTokenKey());
            // 生成token 存入redis
            redisService.set(String.format(RedisKeyConstant.TOKEN_KEY, userPO.getId()), token + "&" + System.currentTimeMillis());

        } catch (Exception e) {
            log.info("用户登录信息加密失败, 原因:", e);
            throw new BaseException(CodeEnum.AUTH_ILLEGAL_LOGIN);
        }
        //添加登入记录
        try {
            LoginRecordPO loginRecordPO = new LoginRecordPO();
            loginRecordPO.setLoginTime(now);
            loginRecordPO.setIp(ip);
            loginRecordPO.setDeviceId(userReq.getDeviceId());
            loginRecordPO.setGmtCreated(now);
            loginRecordPO.setGmtModified(now);
            loginRecordPO.setUserId(userPO.getId());
            loginRecordService.addLoginRecord(loginRecordPO);
        } catch (Exception e) {
            log.info("报存登录记录失败, 原因:", e);
            throw new BaseException(CodeEnum.AUTH_LOGIN_ERR);
        }

        // 查询是否认证二要素
        Integer exist = userMapper.getTwoAuthRecord(userPO.getId());

        Integer identification;
        if (0 == exist) {
            identification = 1;
        } else {
            identification = 0;
        }

        //登录成功，清除登录限制缓存项·
        LocalCache.removeLoginLimit(userPO.getUsername());
        Map<String, Object> map = new HashMap<>(3);

        map.put("userId", userPO.getId());
        map.put("token", token);
        map.put("identification", identification);
        log.info(String.format("用户mobile=%s登录成功，登录结束", userReq.getMobile()));
        return map;
    }

    @Override
    public Map vCodeLogin(UserReq userReq, String ip) {
        Date now = new Date();
        Long nowTime = System.currentTimeMillis();
        String mobile = userReq.getMobile();
        String vCode = userReq.getVerifyCode();
        String deviceId = userReq.getDeviceId();
        String noteToken = userReq.getNoteToken();
        UserPO userPO = userMapper.findUserByMobile(userReq.getMobile());
        if (null == userPO) {
            log.info(String.format("用户%s不存在，登录失败，登录结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_NO_USER);
        }
        //验证短信验证码是否正确
        CodeEnum codeEnum = verifyVCode(mobile, vCode, noteToken, login);
        if (!codeEnum.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            throw new BaseException(CodeEnum.V_CODE_ERR);
        }
        String token;
        try {
            Token userToken = new Token();
            userToken.setLoginTime(nowTime);
            userToken.setId(userPO.getId());
            userToken.setMobile(userPO.getMobile());

            // 生成token 存入redis
            token = AesUtils.aesEncryptHexString(JSON.toJSONString(userToken), appProperties.getTokenKey());
            redisService.set(String.format(RedisKeyConstant.TOKEN_KEY, userPO.getId()), token + "&" + System.currentTimeMillis());
        } catch (Exception e) {
            log.info("用户登录信息加密失败, 原因:", e);
            throw new BaseException(CodeEnum.AUTH_ILLEGAL_LOGIN);
        }
        //添加登入记录
        try {
            LoginRecordPO loginRecordPO = new LoginRecordPO();
            loginRecordPO.setLoginTime(now);
            loginRecordPO.setIp(ip);
            loginRecordPO.setDeviceId(userReq.getDeviceId());
            loginRecordPO.setGmtModified(now);
            loginRecordPO.setGmtCreated(now);
            loginRecordPO.setUserId(userPO.getId());
            loginRecordPO.setDeviceId(deviceId);
            loginRecordService.addLoginRecord(loginRecordPO);
        } catch (Exception e) {
            log.info("报存登录记录失败, 原因:", e);
            throw new BaseException(CodeEnum.AUTH_LOGIN_ERR);
        }
        // 查询是否认证二要素
        Integer exist = userMapper.getTwoAuthRecord(userPO.getId());

        Integer identification;
        if (0 == exist) {
            identification = 1;
        } else {
            identification = 0;
        }
        //登录成功，清除登录限制缓存项
        LocalCache.removeLoginLimit(userPO.getMobile());
        log.info(String.format("用户mobile=%s登录成功，登录结束", userPO.getMobile()));
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userPO.getId());
        map.put("identification", identification);
        map.put("token", token);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map register(UserReq userReq, String ip) {
        log.info(String.format("===>手机号%s开始注册,注册ip为:%s", userReq.getMobile(), ip));
        //检验手机号是否已经注册
        UserPO existUser = userMapper.findUserByMobile(userReq.getMobile());
        if (null != existUser) {
            log.error(String.format("手机号%s，已经被注册，注册失败，注册结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_MOBILE_EXIST);
        }
        //验证短信验证码是否正确
        CodeEnum codeEnum = verifyVCode(userReq.getMobile(), userReq.getVerifyCode(), userReq.getNoteToken(), register);
        if (!codeEnum.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            throw new BaseException(CodeEnum.V_CODE_ERR);
        }

        Date now = new Date();
        Long nowTime = System.currentTimeMillis();
        UserPO userPO = new UserPO();
        userPO.setMobile(userReq.getMobile());
        userPO.setUsername(userReq.getMobile());
        //注册后登录状态为登录
        userPO.setStatus(0);
        userPO.setRegSource(userReq.getRegSource());
        userPO.setPlatform(userReq.getPlatform());
        userPO.setGmtCreated(now);
        userPO.setDeviceId(userReq.getDeviceId());
        // 保存用户
        userMapper.addUser(userPO);
        UserPO user = userMapper.findUserByMobile(userReq.getMobile());

        //添加登入记录
        LoginRecordPO loginRecordPO = new LoginRecordPO();
        loginRecordPO.setUserId(user.getId());
        loginRecordPO.setDeviceId(userReq.getDeviceId());
        loginRecordPO.setGmtCreated(now);
        loginRecordPO.setGmtModified(now);
        loginRecordPO.setIp(ip);
        loginRecordPO.setLoginTime(now);
        try {
            loginRecordService.addLoginRecord(loginRecordPO);
        } catch (Exception e) {
            log.info("报存登录记录失败, 原因:", e);
            throw new BaseException(CodeEnum.AUTH_LOGIN_ERR);
        }

        log.info(String.format("用户mobile=%s注册成功，注册结束", userReq.getMobile()));

        String token;
        Token userToken = new Token();
        userToken.setLoginTime(nowTime);
        userToken.setId(user.getId());
        userToken.setMobile(user.getMobile());
        try {
            token = AesUtils.aesEncryptHexString(JSON.toJSONString(userToken), appProperties.getTokenKey());
        } catch (Exception e) {
            log.info("生成token失败，原因：" + e);
            throw new BaseException(CodeEnum.SYS_UNKNOWN);
        }

        // 生成登录token存入redis
        redisService.set(String.format(RedisKeyConstant.TOKEN_KEY, user.getId()),
                token + "&" + System.currentTimeMillis());
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", user.getId());
        map.put("token", token);
        return map;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CodeEnum serPassword(UserReq userReq) {
        UserPO userPO = userMapper.findUserByMobile(userReq.getMobile());
        if (null == userPO) {
            log.error("设置密码的用户不存在");
            return CodeEnum.AUTH_NO_USER;
        }
        try {
            userPO.setPassword(PasswordUtil.getPassword(userReq.getBeginPassword()));
        } catch (Exception e) {
            log.error(String.format("用户%s的密码加密失败", userReq.getMobile()), e);
            return CodeEnum.SYS_UNKNOWN;
        }
        Date now = new Date();
        Integer row = userMapper.updatePassword(userPO.getId(), userPO.getPassword(), now);
        if (1 != row) {
            log.error(String.format("设置用户%s密码异常", userReq.getMobile()));
            return CodeEnum.AUTH_RESET_PWD_FAIL;
        }
        log.info(String.format("用户%s密码设置成功", userReq.getMobile()));
        return CodeEnum.SUCCESS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CodeEnum resetPassword(UserReq userReq) {
        log.info(String.format("用户%s修改密码", userReq.getMobile()));
        //验证短信验证码是否正确
        CodeEnum codeEnum = verifyVCode(userReq.getMobile(), userReq.getVerifyCode(), userReq.getNoteToken(), resetPwd);
        if (!codeEnum.getCode().equals(CodeEnum.SUCCESS.getCode())) {
            // 如果重设密码的type有误，判断是否为找回密码
            CodeEnum codeEnumFindPwd = verifyVCode(userReq.getMobile(), userReq.getVerifyCode(), userReq.getNoteToken(), findPwd);
            if (!codeEnumFindPwd.getCode().equals(CodeEnum.SUCCESS.getCode())) {
                return CodeEnum.V_CODE_ERR;
            }
        }
        UserPO userPO = userMapper.findUserByMobile(userReq.getMobile());
        if (null == userPO) {
            log.error("修改密码的用户不存在");
            return CodeEnum.AUTH_NO_USER;
        }
        try {
            userPO.setPassword(PasswordUtil.getPassword(userReq.getNewPassword()));
        } catch (Exception e) {
            log.error(String.format("用户%s的密码加密失败", userReq.getMobile()), e);
            return CodeEnum.SYS_UNKNOWN;
        }
        Date now = new Date();
        Integer row = userMapper.updatePassword(userPO.getId(), userPO.getPassword(), now);
        if (1 != row) {
            log.error(String.format("更新用户%s密码异常", userReq.getMobile()));
            return CodeEnum.AUTH_RESET_PWD_FAIL;
        }
        log.info(String.format("用户%s密码修改成功", userReq.getMobile()));
        //设置密码成功，清除登录限制缓存项
        LocalCache.removeLoginLimit(userPO.getUsername());
        return CodeEnum.SUCCESS;
    }

    @Override
    public CodeEnum loginOut(Long userId) {
        //修改为未登录,删除redis中token
        redisService.del(String.format(RedisKeyConstant.TOKEN_KEY, userId));
        redisService.del(String.format(RedisKeyConstant.OLD_TOKEN_KEY, userId));
        return CodeEnum.SUCCESS;
    }

    @Override
    public UserInfoVO getUserInfo(Long userId) {
        UserInfoVO userInfo = userMapper.getUserInfo(userId);
        if (userInfo.getPassword() == null || "".equals(userInfo.getPassword())) {
            userInfo.setExistPassword(false);
        } else {
            userInfo.setExistPassword(true);
        }
        Integer count = userMapper.isSignInToday(userId);
        if (count > 0) {
            userInfo.setSignInToday(true);
            // 设置今日签到获取的信用值
            userInfo.setSignInCreditValue(userMapper.getSignInCreditValue(userId));
        } else {
            userInfo.setSignInToday(false);
        }
        return userInfo;
    }

    @Override
    public List<CityInfoVO> getBuildingStatus() {
        List<CityInfoVO> cityInfoVOList = Lists.newArrayList();

        //查询城市列表
        List<CityConfigPO> cityConfigPOList = userMapper.getBuildingStatus();
        for (CityConfigPO cityConfigPO : cityConfigPOList) {
            CityInfoVO cityInfoVO = new CityInfoVO();
            cityInfoVO.setCityCode(cityConfigPO.getCityCode());
            cityInfoVO.setCityName(cityConfigPO.getCityName());
            cityInfoVO.setStatus(cityConfigPO.getStatus());
            cityInfoVO.setDescriptions(cityConfigPO.getDescription());

            AuthCategoryEnum authCategoryEnum = AuthCategoryEnum.getEnumByCode(cityConfigPO.getCityCode());
            if (authCategoryEnum == null) {
                cityInfoVOList.add(cityInfoVO);
                continue;
            }

            //获取该城市拥有的认证项
            List<Integer> authItemList = AuthItemEnum.listCodeByAuthCategory(authCategoryEnum);
            if (authItemList.isEmpty()) {
                continue;
            }

            Integer willGetCreditValue = 0;
            for (Integer authItem : authItemList) {
                ConfigAuthParamDO configAuthParamDO = configAuthParamMapper.findByAuthItem(authItem);
                //加上首次认证信用值
                willGetCreditValue += configAuthParamDO.getFirstAddCreditValue();
            }

            cityInfoVO.setWillGetCreditValue(willGetCreditValue);

            cityInfoVOList.add(cityInfoVO);
        }

        return cityInfoVOList;
    }

    @Override
    public List<CityInfoVO> getUserBuildingStatus(Long userId) {
        List<CityInfoVO> cityInfoVOList = Lists.newArrayList();

        //查询用户所以认证记录
        List<UserAuthReportDTO> resultList = userMapper.getUserAuthInfo(userId);
        Map<Integer, UserAuthReportDTO> userAuthReportDTOMap = resultList.stream().collect(Collectors.toMap(UserAuthReportDTO::getAuthItem, p -> p));
        //查询城市列表
        List<CityConfigPO> cityConfigPOList = userMapper.getBuildingStatus();
        for (CityConfigPO cityConfigPO : cityConfigPOList) {
            CityInfoVO cityInfoVO = new CityInfoVO();
            cityInfoVO.setCityCode(cityConfigPO.getCityCode());
            cityInfoVO.setCityName(cityConfigPO.getCityName());
            cityInfoVO.setStatus(cityConfigPO.getStatus());
            cityInfoVO.setDescriptions(cityConfigPO.getDescription());

            AuthCategoryEnum authCategoryEnum = AuthCategoryEnum.getEnumByCode(cityConfigPO.getCityCode());
            if (authCategoryEnum == null) {
                cityInfoVOList.add(cityInfoVO);
                continue;
            }

            //获取该城市拥有的认证项
            List<Integer> authItemList = AuthItemEnum.listCodeByAuthCategory(authCategoryEnum);
            if (authItemList.isEmpty()) {
                continue;
            }

            Integer willGetCreditValue = 0;
            for (Integer authItem : authItemList) {
                UserAuthReportDTO userAuthReportDTO = userAuthReportDTOMap.get(authItem);
                ConfigAuthParamDO configAuthParamDO = configAuthParamMapper.findByAuthItem(authItem);
                if (userAuthReportDTO == null) {
                    //未认证过，则加上首次认证信用值
                    willGetCreditValue += configAuthParamDO.getFirstAddCreditValue();
                } else if (AuthStatusEnum.PAST_DUE.getCode().equals(userAuthReportDTO.getStatus())
                        && userAuthReportDTO.getAuthNum() != null && userAuthReportDTO.getAuthNum() > 0) {
                    //认证过期，则加上再次认证信用值
                    willGetCreditValue += configAuthParamDO.getAgainAddCreditValue();
                } else if (AuthStatusEnum.NO_PASS.getCode().equals(userAuthReportDTO.getStatus())
                        || AuthStatusEnum.VERIFICATION.getCode().equals(userAuthReportDTO.getStatus())) {
                    if (userAuthReportDTO.getAuthNum() != null && userAuthReportDTO.getAuthNum() > 0) {
                        //认证过期后 再次认证时不通过或认证中，则加上再次认证信用值
                        willGetCreditValue += configAuthParamDO.getAgainAddCreditValue();
                    } else {
                        //首次认证时不通过或认证中，则加上首次认证信用值
                        willGetCreditValue += configAuthParamDO.getFirstAddCreditValue();
                    }
                }
            }

            cityInfoVO.setWillGetCreditValue(willGetCreditValue);

            cityInfoVOList.add(cityInfoVO);
        }

        return cityInfoVOList;
    }

    @Override
    public List<UserAuthReportVO> getUserAuthInfo(Long userId) {
        List<UserAuthReportVO> userAuthReportVOList = Lists.newArrayList();
        List<UserAuthReportDTO> resultList = userMapper.getUserAuthInfo(userId);

        Map<Integer, UserAuthReportDTO> userAuthReportDTOMap = resultList.stream().collect(Collectors.toMap(UserAuthReportDTO::getAuthItem, p -> p));
        for (AuthItemEnum authItemEnum : AuthItemEnum.values()) {
            UserAuthReportVO authReportVO = new UserAuthReportVO();
            authReportVO.setAuthItem(authItemEnum.getCode());

            UserAuthReportDTO userAuthReportDTO = userAuthReportDTOMap.get(authItemEnum.getCode());
            if (userAuthReportDTO != null) {
                authReportVO.setStatus(userAuthReportDTO.getStatus());

                //判断认证次数是否大于0,
                if (userAuthReportDTO.getAuthNum() != null && userAuthReportDTO.getAuthNum() > 0) {
                    authReportVO.setAddCreditValue(userAuthReportDTO.getAgainAddCreditValue());
                } else {
                    authReportVO.setAddCreditValue(userAuthReportDTO.getFirstAddCreditValue());
                }

                if (AuthStatusEnum.PASS.getCode().equals(userAuthReportDTO.getStatus())) {
                    //判断认证次数是否大于1
                    if (userAuthReportDTO.getAuthNum() != null && userAuthReportDTO.getAuthNum() > 1) {
                        authReportVO.setAddCreditValue(userAuthReportDTO.getAgainAddCreditValue());
                    } else {
                        authReportVO.setAddCreditValue(userAuthReportDTO.getFirstAddCreditValue());
                    }

                    if (userAuthReportDTO.getValidityPeriod() != null && userAuthReportDTO.getValidityPeriod() > 0) {
                        //只有认证通过时，才设置过期时间
                        authReportVO.setValidityDate(DateUtil.addDate(userAuthReportDTO.getAuthTime(), userAuthReportDTO.getValidityPeriod()));
                    }
                }
            } else {
                // 没有的数据状态至为0
                authReportVO.setStatus(0);
                ConfigAuthParamDO configAuthParamDO = configAuthParamMapper.findByAuthItem(authItemEnum.getCode());
                if (configAuthParamDO != null) {
                    authReportVO.setAddCreditValue(configAuthParamDO.getFirstAddCreditValue());
                }
            }

            userAuthReportVOList.add(authReportVO);
        }
        return userAuthReportVOList;
    }

    @Override
    public void updateCreditValue(Long userId, Integer authItem, Integer changeCreditValueEnum) {
        //查询认证信息配置
        ConfigAuthParamDO configAuthParam = configAuthParamMapper.findByAuthItem(authItem);
        //查询认证记录
        UserAuthRecord userAuthRecordDO = userAuthRecordMapper.findByUserIdAndItem(userId, authItem);
        if (configAuthParam != null && userAuthRecordDO != null) {
            //查询信用值增加记录
            List<CreditValueRecord> creditValueRecordList = creditValueRecordService.listByUserIdAndType(userId, changeCreditValueEnum);
            if (creditValueRecordList == null || creditValueRecordList.isEmpty()) {
                if (configAuthParam.getFirstAddCreditValue() != null
                        && configAuthParam.getFirstAddCreditValue() > 0) {
                    //如果该项认证为第一次认证，则加初始的信用值
                    Integer creditValue = userMapper.getCreditValue(userId) + configAuthParam.getFirstAddCreditValue();
                    //修改用户信用值
                    userMapper.updateCreditValue(userId, creditValue);

                    //新增用户信用值修改记录
                    creditValueRecordService.insertCreditValueRecord(userId, configAuthParam.getFirstAddCreditValue(), changeCreditValueEnum);
                }
            } else if (configAuthParam.getAgainAddCreditValue() != null && configAuthParam.getAgainAddCreditValue() > 0) {
                //如果该项认证不是第一次认证，则加多次认证的信用值
                Integer creditValue = userMapper.getCreditValue(userId) + configAuthParam.getAgainAddCreditValue();
                //修改用户信用值
                userMapper.updateCreditValue(userId, creditValue);

                //新增用户信用值修改记录
                creditValueRecordService.insertCreditValueRecord(userId, configAuthParam.getAgainAddCreditValue(), changeCreditValueEnum);
            }
        }
    }

    /**
     * 根据用户的 userId，更新用户的信用值
     *
     * @param userId      用户的userId
     * @param creditValue 信用值
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserCreditValueById(Long userId, Integer creditValue) {
        userMapper.updateUserCreditValueById(userId, creditValue);
    }


    @Override
    public CodeEnum verifyVCode(String mobile, String vCode, String noteToken, Integer flag) {
        try {
            //解密发送短信产生的token
            String params = AesUtils.aesDecryptHexString(noteToken, appProperties.getCodeKey());
            SmsCodeData smsCodeData = JSON.parseObject(params, SmsCodeData.class);
            if (!mobile.equals(smsCodeData.getPhone())) {
                log.info(String.format("注册手机号和获取短信手机号不符,注册的手机号是:%s, 获取短信的手机号为:%s", mobile, smsCodeData.getPhone()));
                return CodeEnum.AUTH_V_CODE_ERR;
            }
            if (!vCode.equals(smsCodeData.getVCode())) {
                log.info(String.format("短信验证码不正确，请求的验证码为%s, 发送的验证码为：%s", vCode, smsCodeData.getVCode()));
                return CodeEnum.AUTH_V_CODE_ERR;
            }
            if (!flag.equals(smsCodeData.getFlag())) {
                log.error("短信验证token可能是非法获取的，请注意");
                return CodeEnum.AUTH_V_CODE_ERR;
            }
            Long now = System.currentTimeMillis();
            //短信验证码大于10分钟失效
            Integer tenMinute = 10 * 60 * 1000;
            if ((now - smsCodeData.getTime()) > tenMinute) {
                log.info(String.format("短信验证码失效，短信验证码生成时间为:%s, 当前时间为:%s", smsCodeData.getTime(), now));
                return CodeEnum.AUTH_V_CODE_LOSE;
            }
        } catch (Exception e) {
            log.error(String.format("手机号%s短信验证码验证异常，原因:", mobile), e);
            return CodeEnum.SYS_UNKNOWN;
        }
        return CodeEnum.SUCCESS;
    }

    @Override
    public Boolean checkPhoneExist(String phone) {
        UserPO userPO = findByMobile(phone);
        return null != userPO;
    }

    @Override
    public void updateUserName(Long userId, String name, Integer gender) {
        userMapper.updateUserName(userId, name, gender);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInviteUserId(Long userId, String mobile, PromotionUser promotionUser) {
        //更新推荐人
        userMapper.updateInviteUserId(userId, mobile);
        //更新推广表
        promotionUserMapper.update(promotionUser);
    }

    @Override
    public Integer countInvites(Long userId) {
        List<Integer> inviteUserIdList = userMapper.countInvites(userId);

        inviteUserIdList.removeIf(inviteUserId
                -> 1 != userMapper.getTwoAuthRecord(Long.valueOf(inviteUserId)));

        return inviteUserIdList.size();
    }

    @Override
    public void updateCreditValueByInvite(Long userId, Integer creditValues) {

        Integer creditValue = userMapper.getCreditValue(userId) + creditValues;
        userMapper.updateCreditValue(userId, creditValue);
    }

    @Override
    public void insertDeviceId(String deviceId) {
        Date gmtCreated = new Date();
        userMapper.insertDeviceId(deviceId, gmtCreated);
    }
}
