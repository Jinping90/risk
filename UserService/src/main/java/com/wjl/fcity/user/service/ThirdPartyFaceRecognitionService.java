package com.wjl.fcity.user.service;

import java.util.Map;

/**
 * @author : Fy
 * @date : 2018-03-31 17:46
 */
public interface ThirdPartyFaceRecognitionService {

    /**
     * 用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的活体数据(liveness_data)进行比对， 来判断是否为同一个人
     *
     * @param userId  用户的userId
     * @param name    用户的真实姓名
     * @param cardNo  用户的身份证号码
     * @param livesId liveness_data的文件id（活体数据）
     * @return map
     */
    Map thirdPartyFaceRecognition(Long userId, String name, String cardNo, String livesId);


}
