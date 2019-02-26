package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 需求 实体类
 */
@Entity
@Table(name = "Demands")
@DynamicInsert
@DynamicUpdate
@JsonView(JsonViewInterface.Simple.class)
public class Demand implements Serializable {
    private static final long serialVersionUID = 3615247090495607558L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false, length = 100)
    private String title; // 标题

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @Column(nullable = false)
    private String cover; // 封面

    @Lob
    @Column(nullable = false)
    private String content; // 内容

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date endTime; // 时间

    @Column(nullable = false, columnDefinition = "Decimal(65,2) default '0.0'")
    private Double budget; // 预算

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Category.class, optional = false)
    private Category category; // 分类

    @Override
    public String toString() {
        return "Demand{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", cover='" + cover + '\'' +
                ", content='" + content + '\'' +
                ", endTime=" + endTime +
                ", budget=" + budget +
                ", category=" + category +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

