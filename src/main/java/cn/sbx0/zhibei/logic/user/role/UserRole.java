package cn.sbx0.zhibei.logic.user.role;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@DynamicInsert
@DynamicUpdate
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true)
    private String code; // 代码

    @Column(nullable = false)
    private Integer weight; // 权值

}
