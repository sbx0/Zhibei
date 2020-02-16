package cn.sbx0.zhibei.logic.user.certification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
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
@Data
public class UserCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
