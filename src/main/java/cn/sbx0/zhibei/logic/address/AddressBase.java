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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // id

    @Column(nullable = false, unique = true, length = 15)
    private String name; // 名称

    private Integer fatherId; // 父亲
}
