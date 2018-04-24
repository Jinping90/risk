package com.wjl.fcity.user.model.vo;

import lombok.Data;

/**
 * @author : Fy
 * @implSpec :
 * @date : 2018-03-29 9:59
 */
@Data
public class IdentifyFaceAuthVO {

    /**
     * 活性文件的id.
     */
    private String livesId;

    /**
     * 人脸照片1
     */
    private String faceImg1;

    /**
     * 人脸照片2.
     */
    private String faceImg2;

    /**
     * 人脸照片3.
     */
    private String faceImg3;

    /**
     * 人脸照片4.
     */
    private String faceImg4;


}
