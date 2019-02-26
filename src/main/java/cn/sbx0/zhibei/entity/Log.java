package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志 实体类
 */
@Entity
@Table(name = "Logs")
@JsonView(JsonViewInterface.All.class)
@DynamicInsert
@DynamicUpdate
public class Log implements Serializable {
    private static final long serialVersionUID = 559080799578351798L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String ip; // IP

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class)
    private User user; // 人物

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 记录时间

    @Column(nullable = false)
    private Long runTime; // 运行时间

    @Column(length = 100)
    private String args; // 参数

    @Column(nullable = false, length = 100)
    private String method; // 方法

    @Column(nullable = false, length = 100)
    private String className; // 类名

    @Column(nullable = false)
    private String url; // url

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", user=" + user +
                ", time=" + time +
                ", runTime=" + runTime +
                ", args='" + args + '\'' +
                ", method='" + method + '\'' +
                ", className='" + className + '\'' +
                ", url='" + url + '\'' +
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
