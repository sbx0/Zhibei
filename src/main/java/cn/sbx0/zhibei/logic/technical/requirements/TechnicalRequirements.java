package cn.sbx0.zhibei.logic.technical.requirements;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 技术需求
 */
@Entity
@Table(name = "technical_requirements")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false)
    private Integer userId; // 发布者

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Lob
    @Column(nullable = false)
    private String context; // 内容

    @Column(nullable = false)
    private String cover; // 封面

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double budget; // 预算

    @Column(nullable = false)
    private String classificationId; // 分类ID

    @Column(nullable = false)
    private String addressId; // 地区ID
}
