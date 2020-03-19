package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分类 实体类
 */
@Entity
@Table(name = "Categories")
@DynamicInsert
@DynamicUpdate
@JsonView(JsonViewInterface.Simple.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    @Column(length = 250)
    private String introduction; // 简介

    @Column(nullable = false)
    private String cover; // 封面

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Category.class)
    private Category father; // 父亲

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", cover='" + cover + '\'' +
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Category getFather() {
        return father;
    }

    public void setFather(Category father) {
        this.father = father;
    }
}
