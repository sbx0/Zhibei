package cn.sbx0.zhibei.logic.user.base;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 基础用户 实体类
 * 只包含常用的必要信息
 */
@Entity
@Table(name = "user_base")
@DynamicInsert
@DynamicUpdate
@Data
public class UserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 30)
    private String name; // 用户名

    private String avatar; // 头像
}
