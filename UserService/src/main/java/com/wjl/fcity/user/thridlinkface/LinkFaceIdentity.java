package com.wjl.fcity.user.thridlinkface;

import com.alibaba.fastjson.JSONObject;
import com.wjl.fcity.user.common.config.LinkFaceConfig;
import com.wjl.fcity.user.common.util.LinkFacePost;
import com.wjl.fcity.user.po.LiveIdNumberVerification;
import com.wjl.fcity.user.po.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author : Fy
 * @date : 2018-04-01 9:36
 */
@Component
@Slf4j
public class LinkFaceIdentity {

    @Resource
    private LinkFaceConfig linkFaceConfig;

    /**
     * 用姓名、身份证号到第三方核查后台查询预留照片，然后与存在云端的活体数据(liveness_data)进行比对， 来判断是否为同一个人
     *
     * @param name        用户的姓名
     * @param idNumber    用户的身份证号码
     * @param livenessId 云端的活体数据(liveness_data)
     * @return ResponseResult
     */
    public ResponseResult<LiveIdNumberVerification> livenessSelfieVerification(String name, String idNumber, String livenessId) {

        //1.0得到第三方的请求路径
        String url = linkFaceConfig.getLivenessSelfieVerification();
        String apiId = linkFaceConfig.getApiId();
        String apiSecret = linkFaceConfig.getApiSecret();

        String responseResult = null;
        try {
            responseResult = LinkFacePost.httpClientPost(url, name, idNumber, livenessId, apiId, apiSecret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("【人脸识别】商汤人脸idNumber={},识别返回信息responseString={}", idNumber, responseResult);

        ResponseResult<LiveIdNumberVerification> sivResult = new ResponseResult<>();
        LiveIdNumberVerification livenessIdNumberVerification = JSONObject.parseObject(responseResult, LiveIdNumberVerification.class);

        if (livenessIdNumberVerification != null) {
            String resultCode = livenessIdNumberVerification.getCode();
            sivResult.setCode(Integer.parseInt(resultCode));
            sivResult.setMsg(livenessIdNumberVerification.getMessage());
            sivResult.setData(livenessIdNumberVerification);
        }



        return sivResult;
    }
}
