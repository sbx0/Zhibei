package cn.sbx0.zhibei.logic.user;

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
public class UserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 30)
    private String name; // 用户名

    private String avatar; // 头像

    @Override
    public String toString() {
        return "UserBase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
