package cn.sbx0.zhibei.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 专家 继承于 基础用户
 */
@Entity
@Table(name = "SPECIALISTS")
@PrimaryKeyJoinColumn(name = "USER_ID")
@DynamicInsert
@DynamicUpdate
public class Specialist extends User {
    private static final long serialVersionUID = 6513915079603852807L;
    @Column(nullable = false, unique = true, length = 20)
    private String phone; // 手机
    @Column(nullable = false, unique = true, length = 100)
    private String email; // 邮箱

    public Specialist() {
        super();
    }

    public Specialist(User user) {
        setName(user.getName());
        setId(user.getId());
        setPassword(user.getPassword());
        setIntroduction(user.getIntroduction());
        setRegisterTime(user.getRegisterTime());
        setBanned(user.getBanned());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    @Override
    public String toString() {
        return "Specialist{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}