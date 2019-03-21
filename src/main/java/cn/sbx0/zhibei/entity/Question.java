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
 * 问题 实体类
 */
@Entity
@Table(name = "Questions")
@DynamicInsert
@DynamicUpdate
public class Question implements Serializable {
    private static final long serialVersionUID = -3315565871221033152L;

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String title; // 标题

    @JsonView(JsonViewInterface.Simple.class)
    @Lob
    @Column(nullable = false)
    private String description; // 描述

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @JsonView(JsonViewInterface.Simple.class)
    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private Double price; // 金额 付给指定回答的人 或者 最佳答案的人

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User quizzer; // 提问者

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class)
    private User appoint; // 指定回答的人

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToMany(cascade = {CascadeType.MERGE}, targetEntity = Tag.class)
    private List<Tag> tags; // 标签

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", price=" + price +
                ", quizzer=" + quizzer +
                ", appoint=" + appoint +
                ", tags=" + tags +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getQuizzer() {
        return quizzer;
    }

    public void setQuizzer(User quizzer) {
        this.quizzer = quizzer;
    }

    public User getAppoint() {
        return appoint;
    }

    public void setAppoint(User appoint) {
        this.appoint = appoint;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
