package com.wjl.fcity.msg.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * app错误码记录表
 * @author czl
 */
@Data
@Entity
@Table(name = "log_app_service")
public class LogAppService {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 错误码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date createTime;
}
