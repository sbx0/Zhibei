package cn.sbx0.zhibei.logic.alipay;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet_base")
@DynamicInsert
@DynamicUpdate
@Data
public class WalletBase {
    @Id
    private Integer userId; // 用户

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    private Double money; // 拥有的钱

    @Column(nullable = false)
    private Boolean finished = Boolean.FALSE; // 是否冻结

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
