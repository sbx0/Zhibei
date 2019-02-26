package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 */
@Entity
@Table(name = "Roles")
@JsonView(JsonViewInterface.All.class)
@DynamicInsert
@DynamicUpdate
public class Role implements Serializable {
    private static final long serialVersionUID = -1252665744119242036L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Column(length = 30)
    private String introduction; // 介绍

    @Column(nullable = false)
    private Boolean available = Boolean.FALSE; // 是否可用

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Role.class)
    private Role father; //  父亲

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = Permission.class)
    private List<Permission> permissions; // 权限

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", available=" + available +
                ", father=" + father +
                ", permissions=" + permissions +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Role getFather() {
        return father;
    }

    public void setFather(Role father) {
        this.father = father;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
