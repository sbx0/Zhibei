package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章 实体类
 */
@Entity
@Table(name = "ARTICLES")
@DynamicInsert
@DynamicUpdate
public class Article implements Serializable {
    private static final long serialVersionUID = -8099382889784133037L;

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String title; // 标题

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @JsonView(JsonViewInterface.Simple.class)
    @Column(length = 250)
    private String introduction; // 简介

    @JsonView(JsonViewInterface.Normal.class)
    @Lob
    @Column(nullable = false)
    private String content; // 内容

    @JsonView(JsonViewInterface.Normal.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastChangeTime; // 上次修改时间

    @JsonView(JsonViewInterface.All.class)
    @Column(length = 40)
    private String password; // 密码

    @JsonView(JsonViewInterface.Normal.class)
    @Column(nullable = false)
    private Integer views; // 查看数

    @JsonView(JsonViewInterface.Normal.class)
    @Column(nullable = false)
    private Integer comments; // 评论数

    @JsonView(JsonViewInterface.Normal.class)
    @Column(nullable = false)
    private Integer likes; // 喜欢数

    @JsonView(JsonViewInterface.Normal.class)
    @Column(nullable = false)
    private Integer dislikes; // 不喜欢数
    /**
     * 默认0 不置顶
     * 大于0 时 越大，排序越靠前
     * 小于0 隐藏
     */
    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false)
    private Integer top; // 置顶排序

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User author; // 作者

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", introduction='" + introduction + '\'' +
                ", content='" + content + '\'' +
                ", lastChangeTime=" + lastChangeTime +
                ", password='" + password + '\'' +
                ", views=" + views +
                ", comments=" + comments +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", top=" + top +
                ", author=" + author +
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
