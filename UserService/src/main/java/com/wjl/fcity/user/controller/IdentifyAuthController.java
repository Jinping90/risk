package com.wjl.fcity.user.controller;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.enums.TwoElementsAuthRecordEnum;
import com.wjl.fcity.user.common.util.IdCardUtil;
import com.wjl.fcity.user.common.util.IpUtil;
import com.wjl.fcity.user.model.vo.IdentifyAuthVO;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.model.vo.IdentifyFaceAuthVO;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.TwoElementAuth;
import com.wjl.fcity.user.model.UserIdCard;
import com.wjl.fcity.user.service.IdentifyAuthService;
import com.wjl.fcity.user.service.TwoElementsAuthenticationService;
import com.wjl.fcity.user.service.UserIdCardService;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author : Fy
 * @date : 2018-03-26 20:11
 * 用户认证模块
 */
@RequestMapping("user/identity")
@Slf4j
@RestController
public class IdentifyAuthController {

    @Resource
    private UserService userService;

    @Resource
    private UserIdCardService userIdCardService;

    @Resource
    private TwoElementsAuthenticationService twoElementsAuthenticationService;

    @Resource
    private IdentifyAuthService identifyAuthService;

    /**
     * 身份证照片认证
     *
     * @param identifyAuthFrom 实名认证页面提交的from表单数据
     * @param request          页面返回的对象
     * @return Response
     */
    @PostMapping(value = "/identify-auth")
    public Response identifyAuth(@RequestBody IdentifyAuthVO identifyAuthFrom, HttpServletRequest request) throws ParseException {

        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【身份证照片认证】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        Long userId = Long.valueOf(userIdStr);
        UserPO user = userService.findOne(userId);
        if (null == user) {
            log.error("【身份证照片认证】用户userId={}不存在!", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "未找到当前用户");
        }

        if (StringUtils.isEmpty(identifyAuthFrom.getName())
                || StringUtils.isEmpty(identifyAuthFrom.getIdCardNo())
                || StringUtils.isEmpty(identifyAuthFrom.getNation())
                || StringUtils.isEmpty(identifyAuthFrom.getGender())
                || StringUtils.isEmpty(identifyAuthFrom.getAddress())
                || StringUtils.isEmpty(identifyAuthFrom.getAgency())
                || StringUtils.isEmpty(identifyAuthFrom.getValidDateBegin())
                || StringUtils.isEmpty(identifyAuthFrom.getFrontImg())
                || StringUtils.isEmpty(identifyAuthFrom.getBackImg())
                || StringUtils.isEmpty(identifyAuthFrom.getHeadImg())) {
            log.error("【身份证照片认证】用户userId={}参数不正确!", userId);
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "参数不正确");
        }

        String idCardNo = identifyAuthFrom.getIdCardNo();
        if (!IdCardUtil.validate(idCardNo)) {
            log.error("【身份证照片认证】用户userId={}的身份证为cardNo={}，验证不通过!", userId, idCardNo);
            return new Response<>(CodeEnum.AUTH_ID_CARD_ERROR, "身份证格式错误!");
        }

        log.info("【身份证照片认证】用户userId={}的身份证为cardNo={}", user.getId(), idCardNo);

        //身份证年满18岁进行校验
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //返回形式为 :20180329
        String birthdayAge = idCardNo.substring(6, 14);
        Integer birthdayAgeInt = Integer.valueOf(birthdayAge);
        Integer nowTime = Integer.valueOf(sdf.format(System.currentTimeMillis()));
        Integer adultAge = 180000;
        Integer compareAge = adultAge.compareTo(nowTime - birthdayAgeInt);
        if (compareAge.equals(1)) {
            log.error("【身份证照片认证】用户userId={}的年龄未满18岁的验证不通过!", userId);
            return new Response<>(CodeEnum.AUTH_UNDERAGE, "年龄未满18岁!");
        }

        //查看用户改身份证是否已经实名认证过
        List<UserIdCard> idCardNoList = userIdCardService.findUserIdCardByIdCardNo(identifyAuthFrom.getIdCardNo());
        long idCardNoSum = idCardNoList
                .stream()
                .filter(userIdCard -> userIdCard.getUserId().equals(userId))
                .count();
        if (0 != idCardNoSum) {
            log.error("【身份证照片认证】用户userId={}，身份证idCardNo={}已经与其他账号绑定!", userId, identifyAuthFrom.getIdCardNo());
            return new Response<>(CodeEnum.AUTH_ID_CARD_BINDING, "身份证号已经与其他账号绑定");
        }

        //性别判断
        String genderStr = identifyAuthFrom.getGender();
        Integer genderInt = "0".equals(genderStr) ? 0 : 1;

        //二要素认证
        TwoElementAuth twoElementAuth = twoElementsAuthenticationService.findTwoElementAuthByUserId(userId);
        if (null == twoElementAuth) {
            log.error("【身份证照片认证】用户userId={},没有进行二要素认证", userId);
            return new Response<>(CodeEnum.AUTH_UN_KNOW_USER, "没有进行二要素认证");

        } else if (!twoElementAuth.getIdCardNo().equals(identifyAuthFrom.getIdCardNo()) || (!twoElementAuth.getName().equals(identifyAuthFrom.getName()))) {
            log.error("【身份证照片认证】用户userId={},用户身份证认证与二要素认证的信息不匹配", userId);
            return new Response<>(CodeEnum.ID_CARD_TWO_FACTOR_AUTH_DIFF, "与二要素认证信息不一致");
        }

        String code = twoElementAuth.getCode();
        if (!(TwoElementsAuthRecordEnum.CERTIFICATION_SUCCESS.getCode().equals(code) || TwoElementsAuthRecordEnum.CERTIFICATION_SUCCESS_NO_PHOTOS.getCode().equals(code))) {
            log.error("【身份证照片认证】用户userId={}二要素认证状态不正确", userId);
            return new Response<>(CodeEnum.AUTH_FAILED, "二要素认证状态不正确");
        }

        identifyAuthService.handleIdentifyAuth(identifyAuthFrom, user, birthdayAge, genderInt);

        return new Response<>(CodeEnum.SUCCESS, "身份证照片认证成功!");
    }


    /**
     * 人脸识别提交接口
     *
     * @param identifyFaceAuthForm 页面提交信息
     * @param request              用户userId
     * @return Response
     */
    @PostMapping(value = "/identify-face-auth")
    public Response identifyFaceAuth(@RequestBody IdentifyFaceAuthVO identifyFaceAuthForm, HttpServletRequest request) {

        String userIdStr = request.getHeader("userId");
        if (StringUtils.isEmpty(userIdStr)) {
            log.error("【人脸识别】参数不正确，用户的userId为空!");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "用户的userId为空!");
        }

        //得到用户登陆的ip的地址
        String ip = IpUtil.getIp(request);

        Long userId = Long.valueOf(userIdStr);
        UserPO user = userService.findOne(userId);

        if (StringUtils.isEmpty(identifyFaceAuthForm.getLivesId())
                || StringUtils.isEmpty(identifyFaceAuthForm.getFaceImg1())
                || StringUtils.isEmpty(identifyFaceAuthForm.getFaceImg2())
                || StringUtils.isEmpty(identifyFaceAuthForm.getFaceImg3())
                || StringUtils.isEmpty(identifyFaceAuthForm.getFaceImg4())) {
            log.error("【人脸识别】用户userId={}参数不正确!", userId);
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "参数不正确");
        }

        if (null == user) {
            log.error("【人脸识别】用户userId={},用户不存在！", userId);
            return new Response<>(CodeEnum.AUTH_USER_NOT_FOUND, "用户的userId为空!");
        }

        TwoElementAuth twoElementAuth = twoElementsAuthenticationService.findTwoElementAuthByUserId(userId);
        if (null == twoElementAuth || null == twoElementAuth.getIdCardNo()) {
            log.error("【人脸识别】用户userId={},没有进行二要素认证", userId);
            return new Response<>(CodeEnum.AUTH_UN_KNOW_USER, "没有进行二要素认证");
        }

        return identifyAuthService.handFaceRecognitionAuth(identifyFaceAuthForm, userId, ip, twoElementAuth.getName(), twoElementAuth.getIdCardNo());
    }
}
