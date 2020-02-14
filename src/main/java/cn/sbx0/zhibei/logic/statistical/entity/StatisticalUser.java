package cn.sbx0.zhibei.logic.statistical.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 统计用户
 */
@Entity
@Table(name = "statistical_user")
public class StatisticalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ip
     */
    @Column(nullable = false, length = 50)
    private String ip;

    /**
     * 客户端
     */
    @Column(nullable = false, length = 50)
    private String client;

    /**
     * 客户端
     */
    @Column(nullable = false)
    private String agent;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time;

    @Override
    public String toString() {
        return "StatisticalUser{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", client='" + client + '\'' +
                ", agent='" + agent + '\'' +
                ", time=" + time +
                '}';
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}