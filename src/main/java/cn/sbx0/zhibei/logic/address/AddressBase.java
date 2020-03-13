package cn.sbx0.zhibei.logic.address;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "address_base")
@DynamicInsert
@DynamicUpdate
@Data
public class AddressBase {
    @Id
    private String id; // id

    @Column(nullable = false)
    private String name; // 名称

    private String fatherId; // 父亲

    private String pinYinPrefix; // 拼音前缀
}
