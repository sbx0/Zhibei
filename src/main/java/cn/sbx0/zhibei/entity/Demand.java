package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 需求 实体类
 */
@Entity
@Table(name = "Demands")
@DynamicInsert
@DynamicUpdate
@JsonView(JsonViewInterface.Simple.class)
public class Demand {

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

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToMany(cascade = {CascadeType.MERGE}, targetEntity = Tag.class)
    private List<Tag> tags; // 标签

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User poster; // 发布人

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
                ", tags=" + tags +
                ", poster=" + poster +
                '}';
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}

