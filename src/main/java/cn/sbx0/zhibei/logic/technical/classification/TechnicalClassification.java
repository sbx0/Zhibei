package cn.sbx0.zhibei.logic.technical.classification;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 技术分类
 */
@Entity
@Table(name = "technical_classification")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Column(length = 250)
    private String introduction; // 简介

    @Column(nullable = false)
    private String cover; // 封面

    private Integer fatherId; // 父亲
}
