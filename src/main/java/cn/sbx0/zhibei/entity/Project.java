package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目
 */
@Entity
@Table(name = "Projects")
@DynamicInsert
@DynamicUpdate
public class Project implements Serializable {
    private static final long serialVersionUID = -8129666995197374818L;

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date createTime; // 创建时间

    @JsonView(JsonViewInterface.Simple.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime; // 结束时间

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User applicant; // 申请人

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User sponsor; // 发起人

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false)
    private Integer status; // 状态

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Demand.class, optional = false)
    private Demand demand; // 需求

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public User getSponsor() {
        return sponsor;
    }

    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Demand getDemand() {
        return demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", applicant=" + applicant +
                ", sponsor=" + sponsor +
                ", status=" + status +
                ", demand=" + demand +
                '}';
    }
}
