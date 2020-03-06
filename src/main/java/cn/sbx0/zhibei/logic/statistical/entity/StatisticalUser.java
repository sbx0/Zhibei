package cn.sbx0.zhibei.logic.statistical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 统计用户
 */
@Entity
@Table(name = "statistical_user")
@Data
public class StatisticalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ip
     */
    @Column(nullable = false, length = 50)
    private String ip;

    /**
     * OperatingSystemClass 是PC还是Mobile还是Tablet
     */
    @Column(nullable = false, length = 10)
    private String client;

    /**
     * AgentNameVersionMajor 浏览器类型
     */
    @Column(nullable = false)
    private String agent;

    /**
     * DeviceName 设备名称
     */
    @Column(nullable = false)
    private String device;

    /**
     * OperatingSystemNameVersionMajor 系统名称
     */
    @Column(nullable = false)
    private String operationSystem;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time;
}