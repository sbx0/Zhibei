package cn.sbx0.zhibei.logic.alipay;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 平台订单 与 具体产品绑定
 */
@Entity
@Table(name = "trade_base")
@DynamicInsert
@DynamicUpdate
@Data
public class TradeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @Column(nullable = false)
    private String tradeNo; // 订单号

    @Column(nullable = false)
    private String productId; // 产品的唯一标识，任何实体都可以成为产品

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double price; // 单价

    @Column(nullable = false)
    private Integer numbers; // 数量

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '1.00'")
    private Double discount; // 折扣

    @Column(nullable = false)
    private String discountType; // 折扣类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }
}
