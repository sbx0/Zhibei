package cn.sbx0.zhibei.logic.technical.achievements;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 技术成果和分类绑定
 */
@Entity
@Table(name = "technical_achievements_and_classificaiton_bind")
@DynamicInsert
@DynamicUpdate
@Data
public class TechnicalAchievementsAndClassificationBind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false)
    private Integer achievementsId; // 成果ID

    @Column(nullable = false)
    private Integer classificationId; // 分类ID
}
