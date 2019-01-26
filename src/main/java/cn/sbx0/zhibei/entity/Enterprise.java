package cn.sbx0.zhibei.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 企业 继承于 基础用户
 */
@Entity
@Table(name = "ENTERPRISES")
@PrimaryKeyJoinColumn(name = "USER_ID")
@DynamicInsert
@DynamicUpdate
public class Enterprise extends User {
    private static final long serialVersionUID = -3829087443787513351L;
    @Column(nullable = false, unique = true, length = 100)
    private String email; // 邮箱
    @Column(nullable = false, unique = true, length = 50)
    private String enterpriseName; // 企业名
    @Column(nullable = false, unique = true, length = 50)
    private String businessLicense; // 营业执照注册号
    @Column(nullable = false)
    private String businessLicenseImg; // 营业执照图片

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "email='" + email + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", businessLicense='" + businessLicense + '\'' +
                ", businessLicenseImg='" + businessLicenseImg + '\'' +
                '}';
    }

}