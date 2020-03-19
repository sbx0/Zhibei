package cn.sbx0.zhibei.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 钱包 实体类
 */
@Entity
@Table(name = "Wallet")
@DynamicInsert
@DynamicUpdate
public class Wallet {

    @JsonView(JsonViewInterface.Simple.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 编号

    @JsonView(JsonViewInterface.Simple.class)
    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double money; // 拥有的钱

    @JsonView(JsonViewInterface.Simple.class)
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class, optional = false)
    private User owner; // 谁的

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", money=" + money +
                ", owner=" + owner +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
