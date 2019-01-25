package cn.sbx0.zhibei.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 */
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {
    private static final long serialVersionUID = -1252665744119242036L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称
    @Column(length = 30)
    private String introduction; // D
    @Column(nullable = false)
    private Boolean available = Boolean.FALSE; // 是否可用，若不可用则无法添加给用户
    // 角色 -- 权限关系：多对多关系;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = Permission.class)
    private List<Permission> permissions; // 权限

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", available=" + available +
                ", permissions=" + permissions +
                '}';
    }

}