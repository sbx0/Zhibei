package cn.sbx0.zhibei.logic.user.role;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@DynamicInsert
@DynamicUpdate
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true)
    private String code; // 代码

    @Column(nullable = false)
    private Integer weight; // 权值

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", weight=" + weight +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
