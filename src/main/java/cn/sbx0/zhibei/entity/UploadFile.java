package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 上传文件 实体类
 */
@Entity
@Table(name = "Files")
@JsonView(JsonViewInterface.All.class)
@DynamicInsert
@DynamicUpdate
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String originalName; // 原始文件名

    @Column(nullable = false, length = 50)
    private String name; // 唯一文件名

    @Column(nullable = false, length = 10)
    private String ext; // 后缀名

    @Column(nullable = false, length = 10)
    private String type; // 文件类型

    @Column(nullable = false, length = 50)
    private String md5; // md5值

    @Column(nullable = false)
    private Long size; // 文件大小

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 上传日期

    @Column(nullable = false)
    private Boolean banned = Boolean.FALSE; // 封禁

    @Override
    public String toString() {
        return "UploadFile{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", name='" + name + '\'' +
                ", ext='" + ext + '\'' +
                ", type='" + type + '\'' +
                ", md5='" + md5 + '\'' +
                ", size=" + size +
                ", time=" + time +
                ", banned=" + banned +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
