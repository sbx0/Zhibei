package cn.sbx0.zhibei.logic.statistical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据
 *
 * @author sbx0
 * @see StatisticalData 实体类
 */
@Entity
@Table(name = "statistical_data")
@Data
public class StatisticalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 种类
     */
    @Column(nullable = false, length = 30)
    private String type;

    /**
     * 分组
     */
    @Column(nullable = false, length = 30)
    private String groupBy;

    /**
     * 数值
     */
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double value;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date recordTime;

}