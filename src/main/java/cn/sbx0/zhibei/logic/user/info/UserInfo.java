package cn.sbx0.zhibei.logic.user.info;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserInfo {
    @Id
    @Column(nullable = false, unique = true, columnDefinition = "Int(11)")
    private Integer userId; // id

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

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", introduction='" + introduction + '\'' +
                ", registerTime=" + registerTime +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", lastTimeOnline=" + lastTimeOnline +
                ", banned=" + banned +
                ", banHours=" + banHours +
                ", integral=" + integral +
                ", level=" + level +
                ", exp=" + exp +
                ", expMax=" + expMax +
                '}';
    }

    public Date getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(Date lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Double getBanHours() {
        return banHours;
    }

    public void setBanHours(Double banHours) {
        this.banHours = banHours;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getExp() {
        return exp;
    }

    public void setExp(Double exp) {
        this.exp = exp;
    }

    public Double getExpMax() {
        return expMax;
    }

    public void setExpMax(Double expMax) {
        this.expMax = expMax;
    }
}
