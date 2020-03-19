package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作 实体类
 * 点赞 / 踩脚 / 收藏
 */
@Entity
@Table(name = "functions")
@DynamicInsert
@DynamicUpdate
public class Function {

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String path; // 页面路径 / 或者唯一字符串 只要唯一即可

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String type; // 点赞 / 踩脚 / 收藏

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User user; // 用户

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", user=" + user +
                ", time=" + time +
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
