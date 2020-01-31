package cn.sbx0.zhibei.logic.statistical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class StatisticalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 种类
     */
    @Column(nullable = false, length = 30)
    private String kind;

    /**
     * 分组
     */
    @Column(nullable = false, length = 30)
    private String grouping;

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
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DataDO{" +
                "id=" + id +
                ", kind='" + kind + '\'' +
                ", grouping='" + grouping + '\'' +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}