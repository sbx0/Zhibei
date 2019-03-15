package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 钱包 数据层
 */
public interface WalletDao extends PagingAndSortingRepository<Wallet, Integer> {

    /**
     * 查找某人的钱包数据
     *
     * @param id
     * @return
     */
    @Query(value = "FROM Wallet WHERE owner.id = ?1")
    Wallet findByUser(Integer id);
}
