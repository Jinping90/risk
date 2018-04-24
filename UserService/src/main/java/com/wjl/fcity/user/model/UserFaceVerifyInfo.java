package com.wjl.fcity.user.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Fy
 * @implSpec :用户人脸识别信息
 * @date : 2018-03-31 15:30
 */
@Data
@Entity
@Table(name = "user_face_verify_info")
public class UserFaceVerifyInfo {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 验证是否通过(0:通过,1:未通过)
     */
    private Integer validStatus;

    /**
     * 验证日期
     */
    private Date validDate;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 人脸匹配度
     */
    private Float matchDegree;

    /**
     * 头像路径1
     */
    private String avatarUrl1;
    /**
     * 头像路径2
     */
    private String avatarUrl2;
    /**
     * 头像路径3
     */
    private String avatarUrl3;
    /**
     * 头像路径4
     */
    private String avatarUrl4;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改时间
     */
    private Date gmtModified;


}
