package cn.sbx0.zhibei.logic.user.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信息
 * 包含普通用户绝大部分信息
 */
@Entity
@Table(name = "user_info")
@DynamicInsert
@DynamicUpdate
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true)
    private Integer userId; // 用户Id

    @Column(nullable = false, unique = true, length = 50)
    private String email; // 邮箱

    @Column(nullable = false, length = 100)
    private String password; // 密码

    @Column(length = 100)
    private String introduction; // 简介

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date registerTime; // 注册时间

    @Column(length = 30)
    private String phone; // 手机

    @Column(columnDefinition = "enum('male','female','')")
    private String sex; // 类型 [男|女|空]

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date birthday; // 生日

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastTimeOnline; // 最后一次在线的时间

    @Column(nullable = false)
    private Boolean banned = Boolean.FALSE; // 封禁

    @Column(nullable = false, columnDefinition = "Decimal(10,1) default '0.0'")
    private Double banHours; // 封禁时长

    @Column(nullable = false, columnDefinition = "Decimal(10,1) default '0.0'")
    private Double integral; // 积分

    @Column(nullable = false, columnDefinition = "Int(11) default '0'")
    private Integer level; // 用户等级

    @Column(nullable = false, columnDefinition = "Decimal(10,1) default '0.0'")
    private Double exp; // 用户经验

    @Column(nullable = false, columnDefinition = "Decimal(10,1) default '100.0'")
    private Double expMax; // 当前等级的最大经验值，超过清空升级

}
