package cn.sbx0.zhibei.logic.user.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户组 实体类
 */
@Entity
@Table(name = "user_group")
@DynamicInsert
@DynamicUpdate
@Data
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 30)
    private String name; // 组名称

    @Column(nullable = false)
    private Integer currentNumber; // 当前成员数

    @Column(nullable = false)
    private Integer limitNumber; // 最多容纳多少组成员

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date createTime; // 创建日期

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date validityTime; // 有效期

    @Column(nullable = false)
    private Integer ownerId; // 创建者Id

    @Version
    private Long version;
}
