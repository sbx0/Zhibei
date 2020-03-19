package cn.sbx0.zhibei.logic.alipay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 订单 数据层
 */
public interface TradeBaseDao extends PagingAndSortingRepository<TradeBase, Integer> {
    /**
     * 根据平台交易单号查询记录
     *
     * @param tradeNo
     * @return
     */
    @Query(value = "select * from trade_base where trade_no = ?1", nativeQuery = true)
    List<TradeBase> findByTradeNo(String tradeNo);
}