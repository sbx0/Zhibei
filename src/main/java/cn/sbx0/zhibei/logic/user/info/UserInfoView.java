package cn.sbx0.zhibei.logic.user.info;

import java.util.Date;

/**
 * 用户信息前台显示
 * 包含普通用户绝大部分信息
 */
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

    @Override
    public String toString() {
        return "UserInfoView{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", registerTime=" + registerTime +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", banned=" + banned +
                ", banHours=" + banHours +
                ", integral=" + integral +
                ", level=" + level +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
