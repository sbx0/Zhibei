package cn.sbx0.zhibei.logic.technical.achievements;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 技术成果
 */
@Entity
@Table(name = "technical_achievements")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalAchievements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double price; // 交易价格

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date postTime; // 发布时间

    @Column(nullable = false)
    private Integer maturity = TechnicalMaturity.inDevelopment.getValue(); // 成熟度

    @Column(nullable = false)
    private Integer cooperationMethod = TechnicalCooperationMethod.other.getValue(); // 合作方式

    @Column(nullable = false)
    private Integer classificationId; // 分类ID

    @Column(nullable = false)
    private Integer addressId; // 地区ID
}
