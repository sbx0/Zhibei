package cn.sbx0.zhibei.logic.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message_base")
@DynamicInsert
@DynamicUpdate
@Data
public class MessageBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false)
    private String content; // 内容

    @Column(nullable = false)
    private Integer sendUserId;

    @Column(nullable = false)
    private Integer receiveUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date sendTime; // 发送时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date receiveTime; // 接收时间

    @Column(nullable = false)
    private String type; // 私聊 群聊 群发 系统通知

    @Column(nullable = false)
    private String link; // link any object
}
