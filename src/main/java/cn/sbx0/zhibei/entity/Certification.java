package cn.sbx0.zhibei.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 认证
 */
@Entity
@Table(name = "CERTIFICATIONS")
@DynamicInsert
@DynamicUpdate
public class Certification implements Serializable {
    private static final long serialVersionUID = -6064596742915995996L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id
    @Column(nullable = false, unique = true, length = 30)
    private String desc; // 认证信息
    @Column(columnDefinition = "enum('personal','enterprise','university','mechanism')")
    private String type; // 类型 [个人|企业|院校|机构]
    @Column(nullable = false)
    private Boolean passed = Boolean.FALSE; // 是否通过
    private Date start_time; // 开始时间
    private Date end_time; // 结束时间
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = User.class, optional = false)
    private User user_id;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", passed=" + passed +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", user_id=" + user_id +
                '}';
    }
}
