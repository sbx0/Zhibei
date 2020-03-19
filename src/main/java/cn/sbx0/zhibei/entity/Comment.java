package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论 实体类
 */
@Entity
@Table(name = "Comments")
@DynamicInsert
@DynamicUpdate
public class Comment {

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String path; // 根据路径加载评论，任何页面都可以添加评论

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false)
    private Integer floor; // 楼层

    @JsonView(JsonViewInterface.Simple.class)
    @Lob
    @Column(nullable = false)
    private String content; // 内容

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false)
    private Integer likes; // 喜欢数

    @JsonView(JsonViewInterface.Simple.class)
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
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Comment.class)
    private Comment father; // 可以嵌套评论

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User poster; // 发布者

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", time=" + time +
                ", floor=" + floor +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", top=" + top +
                ", father=" + father +
                ", poster=" + poster +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Comment getFather() {
        return father;
    }

    public void setFather(Comment father) {
        this.father = father;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}
