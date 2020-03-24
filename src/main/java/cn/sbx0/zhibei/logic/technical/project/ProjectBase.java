package cn.sbx0.zhibei.logic.technical.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "project_base")
@DynamicInsert
@DynamicUpdate
@Data
public class ProjectBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false)
    private String name; // 名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime; // 结束时间

    @Column(nullable = false)
    private Integer applicantId; // 申请人

    @Column(nullable = false)
    private Integer receiveId; // 接收人

    @Column(nullable = false)
    private Integer status; // 状态

    @Column(nullable = false)
    private Integer achievementId; // 成果ID
}
