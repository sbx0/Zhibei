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
    @Column(columnDefinition = "enum('page','action')")
    private String type; // 类型 [page页面|action操作]
    @Column(nullable = false)
    private String url; // 路径
    /**
     * 权限字符串
     * page例子：role:index，
     * action例子：role:create,role:update,role:delete,role:view
     */
    @Column(nullable = false)
    private String str;
    private Integer parentId; // 父编号
    @Column(nullable = false)
    private Boolean available = Boolean.FALSE; // 是否可用

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", str='" + str + '\'' +
                ", parentId=" + parentId +
                ", available=" + available +
                '}';
    }
}