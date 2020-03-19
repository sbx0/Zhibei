package cn.sbx0.zhibei.logic.alipay;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 支付宝 数据层
 */
public interface AlipayBaseDao extends PagingAndSortingRepository<AlipayBase, Integer> {
    /**
     * 根据平台交易单号查询记录
     *
     * @param outTradeNo
     * @return
     */
    @Query(value = "select * from  alipay_base where out_trade_no = ?1", nativeQuery = true)
    AlipayBase findByOutTradeNo(String outTradeNo);
}