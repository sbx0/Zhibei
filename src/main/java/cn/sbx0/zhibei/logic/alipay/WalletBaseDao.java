package cn.sbx0.zhibei.logic.alipay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WalletBaseDao extends PagingAndSortingRepository<WalletBase, Integer> {
    @Query(value = "select * from wallet_base where user_id = ?1", nativeQuery = true)
    WalletBase getUserWallet(Integer userId);
//    @Query(value = "select * from trade_base where aaa = ?1", nativeQuery = true)
//    WalletBase findAllBy(Integer id);
}

