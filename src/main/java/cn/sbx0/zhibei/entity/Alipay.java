package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 支付宝 支付记录
 */
@Entity
@Table(name = "Alipay")
@DynamicInsert
@DynamicUpdate
public class Alipay implements Serializable {
    private static final long serialVersionUID = 855315909328034099L;

    @JsonView(JsonViewInterface.All.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.All.class)
    @Column(unique = true)
    private String outTradeNo; // 订单号

    @JsonView(JsonViewInterface.All.class)
    @Column(unique = true)
    private String tradeNo; // 支付宝订单号

    @JsonView(JsonViewInterface.All.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @Column(nullable = false)
    private Date createTime; // 创建时间

    @JsonView(JsonViewInterface.All.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime; // 结束时间

    @JsonView(JsonViewInterface.All.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User buyer; // 购买者

    @JsonView(JsonViewInterface.All.class)
    @Column(nullable = false, length = 40)
    private String type; // 种类 支付宝

    @JsonView(JsonViewInterface.All.class)
    @ManyToMany(cascade = {CascadeType.MERGE}, targetEntity = Product.class)
    private List<Product> products; // 商品列表

    @JsonView(JsonViewInterface.All.class)
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double amount; // 实付金额

    @JsonView(JsonViewInterface.All.class)
    @Column(nullable = false)
    private Boolean finished = Boolean.FALSE; // 是否完成

    /**
     * 添加商品进入
     *
     * @param product
     */
    public void addProduct(Product product) {
        List<Product> products = getProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        setProducts(products);
    }

    /**
     * 实际付款价格 打折后
     *
     * @return
     */
    public double getRealPay() {
        double total = 0.00;
        for (Product p : getProducts()) {
            if (p.getDiscount() != null)
                p.setPrice(p.getPrice() * p.getDiscount());
            total += p.getPrice();
        }
        return total;
    }

    /**
     * 总体实际价格
     *
     * @return
     */
    public double getRealTotal() {
        double total = 0.00;
        for (Product p : getProducts()) {
            total += p.getPrice();
        }
        return total;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        StringBuilder name = new StringBuilder();
        for (Product p : getProducts()) {
            name.append(p.getName()).append(",");
        }
        if (name.length() > 10)
            name = new StringBuilder(name.substring(0, 10));
        return name.toString();
    }

    /**
     * 获取简介
     *
     * @return
     */
    public String getDesc() {
        StringBuilder desc = new StringBuilder();
        for (Product p : getProducts()) {
            desc.append(p.getName()).append(",");
        }
        if (desc.length() > 40)
            desc = new StringBuilder(desc.substring(0, 10));
        return desc.toString();
    }

    @Override
    public String toString() {
        return "Alipay{" +
                "id=" + id +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", buyer=" + buyer +
                ", type='" + type + '\'' +
                ", products=" + products +
                ", amount=" + amount +
                ", finished=" + finished +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
