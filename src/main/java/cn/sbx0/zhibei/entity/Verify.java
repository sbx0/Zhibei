package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证 用于修改密码
 */
@Entity
@Table(name = "Verifies")
@DynamicInsert
@DynamicUpdate
@JsonView(JsonViewInterface.All.class)
public class Verify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false)
    private String type; // 暂时只用一种 password

    @Column(length = 40, nullable = false)
    private String md5; // 验证地址

    @Column(nullable = false)
    private Boolean used = Boolean.FALSE; // 是否使用过

    @Column(nullable = false)
    private String parameter; // 参数

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time; // 创建时间

    @Override
    public String toString() {
        return "Verify{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", md5='" + md5 + '\'' +
                ", used=" + used +
                ", parameter='" + parameter + '\'' +
                ", time=" + time +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
