package com.wjl.fcity.user.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author czl
 */
@Data
@Entity
@Table(name = "user_auth_record")
public class UserAuthRecord {
    /**
     * 编号.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户编号.
     */
    private Long userId;
    /**
     * 认证类别[1: 居民登记中心, 2: 银行, 3: 手机营业厅, 4: 购物中心].
     */
    private Integer authCategory;
    /**
     * 认证项目[1: 实名认证, 2: 身份证照片, 3: 人脸认证, 4: 信用卡认证, 5: 银行卡认证, 6: 信用卡邮箱, 7: 运营商认证, 8: 支付宝认证, 9: 淘宝认证].
     */
    private Integer authItem;
    /**
     * 结果信息.
     */
    private String authDetail;
    /**
     * 认证评分.
     */
    private Integer authScore;
    /**
     * 认证次数.
     */
    private Integer authNum;
    /**
     * 0:未验证,1:验证中,2:通过,3:不通过,4:已过期.
     */
    private Integer status;
    /**
     * 添加时间.
     */
    private Date gmtCreated;
    /**
     * 更新时间.
     */
    private Date gmtModified;
}