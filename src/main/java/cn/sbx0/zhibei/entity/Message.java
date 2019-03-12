package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息 实体类
 */
@Entity
@Table(name = "MESSAGES")
@DynamicInsert
@DynamicUpdate
public class Message implements Serializable {
    private static final long serialVersionUID = 310841855975160801L;

    @JsonView(JsonViewInterface.Normal.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Normal.class)
    @Column(nullable = false)
    private String content; // 内容

    @JsonView(JsonViewInterface.All.class)
    @Column(nullable = false, length = 30)
    private String ip; // IP

    @JsonView(JsonViewInterface.Normal.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User sendUser; // 发送者

    @JsonView(JsonViewInterface.Normal.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User receiveUser; // 接受者

    @JsonView(JsonViewInterface.Normal.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date sendTime; // 发送时间

    @JsonView(JsonViewInterface.Normal.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date receiveTime; // 接收时间

    @JsonView(JsonViewInterface.All.class)
    @Column(nullable = false, length = 100)
    private String type; // 种类 私聊 群聊 群发 系统通知

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", ip='" + ip + '\'' +
                ", sendUser=" + sendUser +
                ", receiveUser=" + receiveUser +
                ", sendTime=" + sendTime +
                ", receiveTime=" + receiveTime +
                ", type='" + type + '\'' +
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
