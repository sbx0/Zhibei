package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Alipay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * alipay 数据层
 */
public interface AlipayDao extends PagingAndSortingRepository<Alipay, Integer> {
    /**
     * 根据平台交易单号查询记录
     *
     * @param outTradeNo
     * @return
     */
    @Query(value = "FROM Alipay WHERE outTradeNo = ?1")
    Alipay findByOutTradeNo(String outTradeNo);
}
