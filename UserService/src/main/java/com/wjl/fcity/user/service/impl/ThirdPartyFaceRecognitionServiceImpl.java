package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.common.enums.FaceRecognitionEnum;
import com.wjl.fcity.user.common.enums.FaceVerifyEnum;
import com.wjl.fcity.user.po.LiveIdNumberVerification;
import com.wjl.fcity.user.po.ResponseResult;
import com.wjl.fcity.user.service.ThirdPartyFaceRecognitionService;
import com.wjl.fcity.user.service.UserFaceVerifyInfoService;
import com.wjl.fcity.user.thridlinkface.LinkFaceIdentity;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Fy
 * @date : 2018-03-31 17:47
 */
@Service
@Slf4j
public class ThirdPartyFaceRecognitionServiceImpl implements ThirdPartyFaceRecognitionService {

    /**
     * 表示的是人脸识别度要达到0.8以上才算是成功.
     */
    private static final Float RECOGNITION_DEGREE = 0.8F;

    @Resource
    private UserFaceVerifyInfoService userFaceVerifyInfoService;

    @Resource
    private LinkFaceIdentity linkFaceIdentity;

    /**
     * 用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的活体数据(liveness_data)进行比对， 来判断是否为同一个人
     *
     * @param userId  用户的userId
     * @param name    用户的真实姓名
     * @param cardNo  用户的身份证号码
     * @param livesId liveness_data的文件id(活体数据)
     * @return map
     */
    @Override
    public Map thirdPartyFaceRecognition(Long userId, String name, String cardNo, String livesId) {

        Map<String, Object> retData = Maps.newHashMap();
        ResponseResult<LiveIdNumberVerification> responseResult = linkFaceIdentity.livenessSelfieVerification(name, cardNo, livesId);

        if (Objects.equals(responseResult.getCode(), FaceRecognitionEnum.AUTHENTICATION_THROUGH.getCode())) {
            //认证成功
            LiveIdNumberVerification liv = responseResult.getData();
            if (liv.getVerificationScore() == null) {
                retData.put("code", 1);
                retData.put("msg", "身份认证未通过!");
            }

            float confidence = Float.parseFloat(liv.getVerificationScore());

            ///UserFaceVerifyInfo userFaceVerifyInfo = userFaceVerifyInfoService.findUserFaceVerifyInfoByUserId(userId);
            ///userFaceVerifyInfo.setMatchDegree(confidence);

            if (Float.compare(confidence, RECOGNITION_DEGREE) >= 0) {
                //人脸识别成功
                log.info("【人脸识别】商汤人脸识别userId={},开始保存人脸识别相似度", userId);
                userFaceVerifyInfoService.updateUserFaceVerifyInfoMatchDegreeByUserId(userId, confidence, FaceVerifyEnum.ADOPT.getCode());
                retData.put("code", 2);
                retData.put("score", (int) (confidence * 100));
                retData.put("msg", "人脸认证成功");
            } else {
                retData.put("code", 3);
                retData.put("msg", "人脸比对分数过低!");
            }

        } else if (Objects.equals(responseResult.getCode(), FaceRecognitionEnum.NAME_AND_ID_CARD_NOT_MATCH.getCode())) {
            retData.put("code", 3);
            retData.put("msg", "姓名与身份证号不匹配!");
        } else if (Objects.equals(responseResult.getCode(), FaceRecognitionEnum.INVALID_IDENTITY_CARD_NUMBER.getCode())) {
            retData.put("code", 3);
            retData.put("msg", "身份证号无效!");
        } else if (Objects.equals(responseResult.getCode(), FaceRecognitionEnum.DETECTION_FAILURE.getCode())) {
            retData.put("code", 3);
            retData.put("msg", "检测失败!");
        } else if (Objects.equals(responseResult.getCode(), FaceRecognitionEnum.RESOURCES_NOT_FOUND.getCode())) {
            retData.put("code", 3);
            retData.put("msg", "云端资源未找到!");
        } else {
            retData.put("code", 3);
            retData.put("msg", "人脸活体比对失败!");
        }

        return retData;
    }
}
