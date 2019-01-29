package cn.sbx0.zhibei.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 权限表
 */
@Entity
@Table(name = "PERMISSIONS")
@DynamicInsert
@DynamicUpdate
public class Permission implements Serializable {
    private static final long serialVersionUID = 8640097004258127319L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称
    @Column(length = 30)
    private String introduction; // 介绍
    @Column(columnDefinition = "enum('page','action')")
    private String type; // 类型 [page页面|action操作]
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
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, targetEntity = Permission.class)
    private Permission father; // 父亲

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", str='" + str + '\'' +
                ", available=" + available +
                ", father=" + father +
                '}';
    }
}