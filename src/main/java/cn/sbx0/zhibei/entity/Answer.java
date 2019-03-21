package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 回答 实体类
 */
@Entity
@Table(name = "Answers")
@DynamicInsert
@DynamicUpdate
public class Answer implements Serializable {
    private static final long serialVersionUID = -5728248622466922136L;
    /**
     * TODO Answer
     */
    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Lob
    @Column(nullable = false)
    private String content; // 内容

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User answerer; // 回答者

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Question.class, optional = false)
    private Question question; // 问题

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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answerer=" + answerer +
                ", question=" + question +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", top=" + top +
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAnswerer() {
        return answerer;
    }

    public void setAnswerer(User answerer) {
        this.answerer = answerer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
}
