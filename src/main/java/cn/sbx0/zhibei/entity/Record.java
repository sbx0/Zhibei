package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 记录 实体类
 * 购买某些页面的查看权
 */
@Entity
@Table(name = "Record")
@DynamicInsert
@DynamicUpdate
public class Record {

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 时间

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double cost; // 花费

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double balance; // 余额

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, length = 100)
    private String path; // 页面路径 / 或者唯一字符串 只要唯一即可

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User user; // 用户

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", time=" + time +
                ", cost=" + cost +
                ", balance=" + balance +
                ", path='" + path + '\'' +
                ", user=" + user +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
