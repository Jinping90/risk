package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.common.config.AliOssConfig;
import com.wjl.fcity.user.common.enums.*;
import com.wjl.fcity.user.common.util.OssServerClient;
import com.wjl.fcity.user.mapper.ConfigAuthParamMapper;
import com.wjl.fcity.user.model.*;
import com.wjl.fcity.user.model.vo.IdentifyAuthVO;
import com.wjl.fcity.user.model.vo.IdentifyFaceAuthVO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author : Fy
 * @implSpec : 身份证认证
 * @date : 2018-03-29 11:49
 */
@Service
@Slf4j
public class IdentifyAuthServiceImpl implements IdentifyAuthService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Resource
    private AliOssConfig aliOssConfig;

    @Resource
    private UserIdCardService userIdCardService;

    @Resource
    private UserAuthRecordService userAuthRecordService;

    @Resource
    private UserFaceVerifyInfoService userFaceVerifyInfoService;

    @Resource
    private ThirdPartyFaceRecognitionServiceImpl thirdPartyFaceRecognitionService;

    @Resource
    private UserService userService;

    @Resource
    private ConfigAuthParamMapper configAuthParamMapper;

    @Resource
    private CreditValueRecordService creditValueRecordService;

    /**
     * 处理实名认证(身份证认证)
     *
     * @param identifyAuthFrom 页面上实名认证封装对象
     * @param user             用户的信息
     * @param birthdayAge      用户的生日
     * @param genderInt        性别[0: 男, 1: 女]
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleIdentifyAuth(IdentifyAuthVO identifyAuthFrom, UserPO user, String birthdayAge, Integer genderInt) throws ParseException {

        Long userId = user.getId();
        //将人脸识别照片传到阿里的服务器上去
        log.info("【身份证照片认证】用户userId={}开始实名认证,将base64位加密后的[身份证正面照]传送到Oss上面去", userId);
        String frontImgUrl = OssServerClient.putObjects(identifyAuthFrom.getFrontImg(), userId, aliOssConfig);

        log.info("【身份证照片认证】用户userId={}开始实名认证,将base64位加密后的[身份证反面照]传送到Oss上面去", userId);
        String backImgUrl = OssServerClient.putObjects(identifyAuthFrom.getBackImg(), userId, aliOssConfig);

        log.info("【身份证照片认证】用户userId={}开始实名认证,将base64位加密后的[身份证头像照]传送到Oss上面去", userId);
        String dbImgUrl = OssServerClient.putObjects(identifyAuthFrom.getHeadImg(), userId, aliOssConfig);

        UserIdCard userIdCard = new UserIdCard();
        userIdCard.setUserId(userId);
        userIdCard.setName(identifyAuthFrom.getName());
        userIdCard.setIdCardNo(identifyAuthFrom.getIdCardNo());
        userIdCard.setAddress(identifyAuthFrom.getAddress());
        userIdCard.setAgency(identifyAuthFrom.getAgency());
        userIdCard.setBirthday(new SimpleDateFormat("yyyyMMdd").parse(birthdayAge));
        userIdCard.setGender(genderInt);
        userIdCard.setNation(identifyAuthFrom.getNation());
        userIdCard.setValidDateBegin(sdf.parse(identifyAuthFrom.getValidDateBegin()));

        //身份证有效期时间可能是永久，数据库存为null，代表永久
        String validDateEnd = identifyAuthFrom.getValidDateEnd();
        if (null == validDateEnd) {
            userIdCard.setValidDateEnd(null);
        } else {
            userIdCard.setValidDateEnd(sdf.parse(identifyAuthFrom.getValidDateEnd()));
        }

        userIdCard.setBackImgUrl(backImgUrl);
        userIdCard.setFrontImgUrl(frontImgUrl);
        userIdCard.setDbImgUrl(dbImgUrl);
        userIdCard.setStatus(AuthStatusEnum.WAIT_ACTIVE.getCode());
        userIdCard.setGmtCreated(new Date());
        userIdCard.setGmtModified(new Date());

        log.info("【身份证照片认证】用户userId={}身份证信息保存到数据库", userId);
        userIdCardService.insertUserIdCard(userIdCard);

        log.info("【身份证照片认证】用户userId={}身份验证以通过,将用户身份证认证信息保存到用户认证记录表", userId);
        UserAuthRecord oldUserAuthRecord = userAuthRecordService.findUserAuthRecordByUserId(userId, AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode(), AuthItemEnum.ID_CARD_PHOTO.getCode());
        //用户认证记录表不存在在表中插入新的数据
        if (null != oldUserAuthRecord) {
            //先更新状态,认证次数加一
            oldUserAuthRecord.setStatus(AuthStatusEnum.PASS.getCode());
            userAuthRecordService.updateUserAuthRecordStatus(oldUserAuthRecord);
            log.info("【身份证照片认证】用户userId={}身份证信息状态更新未未认证，开始进行身份证认证", userId);
        } else {
            //表示该用户第一次身份证认证，将该数据插入到数据库中
            UserAuthRecord userAuthRecord = new UserAuthRecord();
            userAuthRecord.setUserId(userId);
            userAuthRecord.setAuthCategory(AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode());
            userAuthRecord.setAuthItem(AuthItemEnum.ID_CARD_PHOTO.getCode());
            userAuthRecord.setAuthDetail("身份证认证");
            userAuthRecord.setAuthScore(100);
            //第一次认证，调用后，开始修改身份证的认证次数
            userAuthRecord.setAuthNum(1);
            userAuthRecord.setStatus(AuthStatusEnum.PASS.getCode());
            userAuthRecord.setGmtCreated(new Date());
            userAuthRecord.setGmtModified(new Date());

            //将用户身份证认证信息存在用户认证记录表中
            userAuthRecordService.insertUserAuthRecord(userAuthRecord);
            log.info("【身份证照片认证】用户userId={}身份证信息保存到数据库", userId);
        }

        userIdCard.setStatus(AuthStatusEnum.PASS.getCode());

        userIdCardService.updateUserIdCard(AuthStatusEnum.PASS.getCode(), userId);

        log.info("【身份证照片认证】用户userId={}，完成身份证认证", userId);
    }

    /**
     * 人脸识别接口
     *
     * @param identifyFaceAuthForm app端传过来的封装的参数
     * @param userId               用户的userId
     * @param ip                   app端的ip地址
     * @param name                 用户的名字
     * @param idCard               用户的身份证号码
     * @return Response
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response handFaceRecognitionAuth(IdentifyFaceAuthVO identifyFaceAuthForm, Long userId, String ip, String name, String idCard) {

        log.info("【人脸识别】用户userId={}开始人脸识别,将base64位加密后的[人脸照片]传送到Oss上面去", userId);
        String avatarUrl1 = OssServerClient.putObjects(identifyFaceAuthForm.getFaceImg1(), userId, aliOssConfig);
        String avatarUrl2 = OssServerClient.putObjects(identifyFaceAuthForm.getFaceImg2(), userId, aliOssConfig);
        String avatarUrl3 = OssServerClient.putObjects(identifyFaceAuthForm.getFaceImg3(), userId, aliOssConfig);
        String avatarUrl4 = OssServerClient.putObjects(identifyFaceAuthForm.getFaceImg4(), userId, aliOssConfig);

        log.info("【人脸识别】用户userId={}将保存人脸识别的照片", userId);
        UserFaceVerifyInfo userFaceVerifyInfo = new UserFaceVerifyInfo();
        userFaceVerifyInfo.setUserId(userId);
        userFaceVerifyInfo.setValidStatus(FaceVerifyEnum.NOT_THROUGH.getCode());
        userFaceVerifyInfo.setValidDate(new Date());
        userFaceVerifyInfo.setIp(ip);
        userFaceVerifyInfo.setAvatarUrl1(avatarUrl1);
        userFaceVerifyInfo.setAvatarUrl2(avatarUrl2);
        userFaceVerifyInfo.setAvatarUrl3(avatarUrl3);
        userFaceVerifyInfo.setAvatarUrl4(avatarUrl4);
        userFaceVerifyInfo.setGmtCreated(new Date());
        userFaceVerifyInfo.setGmtModified(new Date());

        //如果用户已经在用户人脸识别信息表中存在信息，那么就更新这条记录，否则就插入新的记录
        userFaceVerifyInfoService.insertOrUpdateUserFaceVerifyInfo(userFaceVerifyInfo);

        //开始调用商汤(第三方)的人脸识别接口
        Map mapResult = thirdPartyFaceRecognitionService.thirdPartyFaceRecognition(userId, name, idCard, identifyFaceAuthForm.getLivesId());

        log.info("【人脸识别】用户userId={}调用第三方商汤人脸识别返回mapResult={}", userId, mapResult);
        int code = (int) mapResult.get("code");

        //查找用户认证的次数
        UserAuthRecord oldUserAuthRecord = userAuthRecordService.findUserAuthRecordByUserId(userId, AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode(), AuthItemEnum.ID_CARD_FACE.getCode());

        int score = 0;
        Object scoreObject = mapResult.get("score");
        if (null != scoreObject) {
            score = (int) scoreObject;
        }

        if (null != oldUserAuthRecord) {
            //先更新状态,认证次数加一
            oldUserAuthRecord.setStatus(code);
            String msg = (String) mapResult.get("msg");
            oldUserAuthRecord.setAuthDetail(msg);
            oldUserAuthRecord.setAuthScore(score);
            userAuthRecordService.updateUserAuthRecordStatus(oldUserAuthRecord);
        } else {
            log.info("【人脸识别】用户userId={}身份证认证通过，将人脸识别信息保存到用户认证记录表中", userId);
            UserAuthRecord userAuthRecord = new UserAuthRecord();
            userAuthRecord.setUserId(userId);
            userAuthRecord.setAuthCategory(AuthCategoryEnum.RESIDENT_REGISTRATION_CENTRE.getCode());
            userAuthRecord.setAuthItem(AuthItemEnum.ID_CARD_FACE.getCode());
            String msg = (String) mapResult.get("msg");
            userAuthRecord.setAuthDetail(msg);
            userAuthRecord.setAuthNum(1);
            userAuthRecord.setAuthScore(score);
            userAuthRecord.setStatus(code);
            userAuthRecord.setGmtCreated(new Date());
            userAuthRecord.setGmtModified(new Date());

            //将用户身份证认证信息存在用户认证记录表中
            userAuthRecordService.insertUserAuthRecord(userAuthRecord);
            log.info("【人脸识别】用户userId={}身份证信息保存到数据库", userId);
        }

        // 用户人脸识别信息状态码转化(从第三方返回来的code为2代表人脸识别通过)      验证是否通过(0:通过,1:未通过)
        Integer codeStatus = code == 2 ? FaceVerifyEnum.ADOPT.getCode() : FaceVerifyEnum.NOT_THROUGH.getCode();

        userFaceVerifyInfoService.updateUserFaceVerifyInfoStatusByUserId(codeStatus, userId);

        //(0:通过,1:未通过)
        if (codeStatus.equals(0)) {
            //居民等级中心：高级实名认证后
            ConfigAuthParamDO configAuthParam = configAuthParamMapper.findByAuthItem(AuthItemEnum.ID_CARD_FACE.getCode());

            Integer certificationCreditValue = configAuthParam.getFirstAddCreditValue();
            userService.updateUserCreditValueById(userId, certificationCreditValue);

            //将本次用户信用值的变化插入到用户信用值记录表中
            CreditValueRecord creditValueRecord = new CreditValueRecord();
            creditValueRecord.setUserId(userId);
            creditValueRecord.setChangeCreditValue(certificationCreditValue);
            creditValueRecord.setType(CreditValueRecordEnum.FACE_AUTHENTICATION.getStatus());
            creditValueRecord.setGmtCreated(new Date());
            creditValueRecord.setGmtModified(new Date());
            creditValueRecordService.insertCreditValueRecord(creditValueRecord);

            log.info("【人脸识别】用户userId={}，完成人脸识别认证", userId);
            return new Response<>(CodeEnum.SUCCESS, (String) mapResult.get("msg"));
        } else {
            log.info("【人脸识别】用户userId={}，人脸识别认证失败", userId);
            return new Response<>(CodeEnum.FACE_RECOGNITION, (String) mapResult.get("msg"));
        }
    }
}
