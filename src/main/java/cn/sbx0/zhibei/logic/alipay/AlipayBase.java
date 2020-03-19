package cn.sbx0.zhibei.logic.alipay;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 支付宝
 */
@Entity
@Table(name = "alipay_base")
@DynamicInsert
@DynamicUpdate
@Data
public class AlipayBase {
    @Id
    private String outTradeNo; // 商户订单号

    @Column(unique = true)
    private String tradeNo; // 支付宝订单号

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime; // 结束时间

    private Integer buyerId; // 购买者

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double amount; // 实付金额

    @Column(nullable = false)
    private Boolean finished = Boolean.FALSE; // 是否完成

}
