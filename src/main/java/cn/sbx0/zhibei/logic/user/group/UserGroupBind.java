package cn.sbx0.zhibei.logic.user.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_group_bind")
@DynamicInsert
@DynamicUpdate
@Data
public class UserGroupBind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false)
    private Integer userId; // 用户Id

    @Column(nullable = false)
    private Integer groupId; // 角色Id

    @Column(nullable = false)
    private boolean isAdmin; // 是否是管理员

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date validityTime; // 角色有效期

}
