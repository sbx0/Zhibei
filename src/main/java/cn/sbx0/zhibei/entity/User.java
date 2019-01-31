package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础用户
 */
@Entity
@Table(name = "USERS")
@DynamicInsert
@DynamicUpdate
public class User implements Serializable {
    private static final long serialVersionUID = -7669301273984030395L;
    @JsonView(Normal.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id
    @JsonView(Normal.class)
    @Column(nullable = false, unique = true, length = 15)
    private String name; // 用户名
    @JsonView(Admin.class)
    @Column(nullable = false, length = 100)
    private String password; // 密码
    @JsonView(Normal.class)
    @Column(length = 30)
    private String introduction;
    @JsonView(Admin.class)
    @Column(nullable = false)
    private Date registerTime; // 注册时间
    @JsonView(Admin.class)
    @Column(nullable = false)
    private Boolean banned = Boolean.FALSE; // 封禁
    @JsonView(Admin.class)
    @Column(length = 30)
    private String phone; // 手机
    @JsonView(Admin.class)
    @Column(length = 50)
    private String email; // 邮箱
    @JsonView(Admin.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Role.class)
    private Role role; // 角色

    /**
     * 正常情况下需要转换成Json的属性
     */
    public interface Normal {

    }

    /**
     * 我全都要
     */
    public interface Admin extends Normal {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", introduction='" + introduction + '\'' +
                ", registerTime=" + registerTime +
                ", banned=" + banned +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}