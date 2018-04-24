package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户人脸识别信息
 *
 * @author czl
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
     * 头像Base64
     */
    private String avatar;
    /**
     * 添加ip
     */
    private String addIp;
    /**
     * 人脸匹配度
     */
    private String matchDegree;
    /**
     * 头像路径
     */
    private String avatarDir;
    /**
     * 头像路径2
     */
    private String avatarDir2;
    /**
     * 头像路径3
     */
    private String avatarDir3;
    /**
     * 头像路径4
     */
    private String avatarDir4;
    /**
     * 检出状态
     */
    private Integer exportStatus = 0;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
