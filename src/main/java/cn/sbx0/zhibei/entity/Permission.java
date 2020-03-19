package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限 实体类
 */
@Entity
@Table(name = "Permissions")
@JsonView(JsonViewInterface.All.class)
@DynamicInsert
@DynamicUpdate
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Column(length = 30)
    private String introduction; // 介绍

    /**
     * 资源路径
     * 例子
     * /user/*
     * /*
     * /user/add
     */
    @Column(nullable = false)
    private String url; // 路径

    /**
     * 权限字符串
     * page例子：0 或 1
     * action例子: 0000 或 0001 或 0011 或 0111 或 1111 或 * 删 > 改 > 增 > 查
     */
    @Column(nullable = false)
    private String str; // 权限字符串

    @Column(nullable = false)
    private Boolean available = Boolean.FALSE; // 是否可用

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Permission.class)
    private Permission father; // 父亲

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", url='" + url + '\'' +
                ", str='" + str + '\'' +
                ", available=" + available +
                ", father=" + father +
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Permission getFather() {
        return father;
    }

    public void setFather(Permission father) {
        this.father = father;
    }
}
