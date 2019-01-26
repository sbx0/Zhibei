package cn.sbx0.zhibei.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 院校&科研机构 继承于 基础用户
 */
@Entity
@Table(name = "UNIVERSITIES")
@PrimaryKeyJoinColumn(name = "USER_ID")
@DynamicInsert
@DynamicUpdate
public class University extends User {
    private static final long serialVersionUID = 1225308028817544098L;
    @Column(nullable = false, unique = true, length = 100)
    private String email; // 邮箱
    @Column(nullable = false, unique = true, length = 50)
    private String universityName; // 认证名称
    @Column(nullable = false)
    private String universityOfficialSeal; // 认证申请文件加盖公章后的图片

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityOfficialSeal() {
        return universityOfficialSeal;
    }

    public void setUniversityOfficialSeal(String universityOfficialSeal) {
        this.universityOfficialSeal = universityOfficialSeal;
    }

    @Override
    public String toString() {
        return "University{" +
                "email='" + email + '\'' +
                ", universityName='" + universityName + '\'' +
                ", universityOfficialSeal='" + universityOfficialSeal + '\'' +
                '}';
    }

}