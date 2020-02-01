package cn.sbx0.zhibei.logic.user.certification;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户认证
 * 包含详细的认证信息
 */
@Entity
@Table(name = "user_certification")
@DynamicInsert
@DynamicUpdate
public class UserCertification {
    @Id
    private Integer id; // id

    @Column(nullable = false)
    private Integer userId; // 用户Id

    @Column(nullable = false)
    private Integer status = CertificationStatus.underConfirm.getValue(); // 认证状态

    @Column(nullable = false)
    private Integer kind = CertificationType.personal.getValue(); // 认证种类

    @Lob
    @Column(nullable = false)
    private String material; // 认证材料

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date validityTime; // 认证有效期

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date submitTime; // 认证提交时间

    @Override
    public String toString() {
        return "UserCertification{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", kind=" + kind +
                ", material='" + material + '\'' +
                ", validityTime=" + validityTime +
                ", submitTime=" + submitTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}
