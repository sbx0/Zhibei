package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付宝 服务层
 */
@Service
public class AlipayBaseService extends BaseService<AlipayBase, Integer> {
    @Resource
    private AlipayBaseDao dao;

    @Override
    public PagingAndSortingRepository<AlipayBase, Integer> getDao() {
        return dao;
    }

    @Override
    public AlipayBase getEntity() {
        return new AlipayBase();
    }

    @Override
    public boolean checkDataValidity(AlipayBase object) {
        return true;
    }

    public AlipayBase findByOutTradeNo(String outTradeNo) {
        return dao.findByOutTradeNo(outTradeNo);
    }
}
