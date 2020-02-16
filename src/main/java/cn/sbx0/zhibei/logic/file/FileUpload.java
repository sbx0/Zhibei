package cn.sbx0.zhibei.logic.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 上传文件 实体类
 */
@Entity
@Table(name = "file_upload")
@DynamicInsert
@DynamicUpdate
@Data
public class FileUpload {
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
    private Integer userId; // 用户Id

    @Column(nullable = false)
    private boolean isPublic = false; // 是否公开
}
