package com.wjl.fcity.user.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author : Fy
 * @implSpec : Created with IntelliJ IDEA.
 * @date : 2018-03-26 20:18
 * 实名认证接口表单提交对象
 */
@Data
public class IdentifyAuthVO {
    /**
     * 身份证姓名.
     */
    public String name;

    /**
     * 身份证号.
     */
    public String idCardNo;

    /**
     * 民族.
     */
    public String nation;

    /**
     * 性别 0:男;1:女.
     */
    public String gender;

    /**
     * 地址.
     */
    @NotBlank(message = "地址不能为空")
    public String address;

    /**
     * 发证机关.
     */
    public String agency;

    /**
     * 有效期-开始(YYYY-mm-dd格式).
     */
    public String validDateBegin;

    /**
     * 有效期-结束(YYYY-mm-dd格式).
     */
    public String validDateEnd;

    /**
     * 身份证正面照.
     */
    public String frontImg;

    /**
     * 身份证反面照.
     */
    public String backImg;

    /**
     * 身份证头像照.
     */
    public String headImg;
}
