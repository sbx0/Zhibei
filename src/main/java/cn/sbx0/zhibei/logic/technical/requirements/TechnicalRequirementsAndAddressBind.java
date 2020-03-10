package cn.sbx0.zhibei.logic.technical.requirements;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "technical_requirement_and_address_bind")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalRequirementsAndAddressBind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false)
    private Integer requirementId; // 需求ID

    @Column(nullable = false)
    private Integer addressId; // 地区ID
}
