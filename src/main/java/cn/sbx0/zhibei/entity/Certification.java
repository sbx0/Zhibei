package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 认证 实体类
 */
@Entity
@Table(name = "CERTIFICATIONS")
@JsonView(JsonViewInterface.Normal.class)
@DynamicInsert
@DynamicUpdate
public class Certification implements Serializable {
    private static final long serialVersionUID = -6064596742915995996L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 30)
    private String info; // 认证信息

    @Column(columnDefinition = "enum('personal','admin','enterprise','university','mechanism')")
    private String type; // 类型 [个人|管理员|企业|院校|机构]

    private Boolean passed = Boolean.FALSE; // 是否通过

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime; // 开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime; // 结束时间

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User user;

    @Column(nullable = false)
    private String license; // 营业执照 或 机构名称 或 身份证信息

    @Column(nullable = false)
    private String img; // 申请文件

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", type='" + type + '\'' +
                ", passed=" + passed +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", user=" + user +
                ", license='" + license + '\'' +
                ", img='" + img + '\'' +
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
