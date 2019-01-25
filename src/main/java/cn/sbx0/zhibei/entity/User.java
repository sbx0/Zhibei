package cn.sbx0.zhibei.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础用户
 */
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    private static final long serialVersionUID = -7669301273984030395L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id
    @Column(nullable = false, unique = true, length = 15)
    private String name; // 用户名
    @Column(nullable = false, length = 50)
    private String password; // 密码
    @Column(length = 30)
    private String introduction;
    @Column(nullable = false)
    private Date registerTime; // 注册时间
    @Column(nullable = false)
    private Boolean banned = Boolean.FALSE; // 封禁
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = Role.class, optional = false)
    private Role role; // 角色

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
                ", role=" + role +
                '}';
    }

}