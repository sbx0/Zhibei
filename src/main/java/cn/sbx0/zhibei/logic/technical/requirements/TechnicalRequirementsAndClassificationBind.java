package cn.sbx0.zhibei.logic.technical.requirements;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "technical_requirement_and_classificaiton_bind")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalRequirementsAndClassificationBind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false)
    private Integer requirementId; // 需求ID

    @Column(nullable = false)
    private Integer classificationId; // 分类ID
}
