package cn.sbx0.zhibei.logic.user.info;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息前台显示
 * 包含普通用户绝大部分信息
 */
@Data
public class UserInfoView {
    private Integer id; // id
    private String email; // 邮箱
    private String name; // 姓名
    private String introduction; // 简介
    private Date registerTime; // 注册时间
    private String phone; // 手机
    private String sex; // 类型 [男|女|空]
    private Date birthday; // 生日
    private Boolean banned; // 封禁
    private Double banHours; // 封禁时长
    private Double integral; // 积分
    private Integer level; // 用户等级
}
