package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WalletBaseService extends BaseService<WalletBase, Integer> {
    @Resource
    private WalletBaseDao dao;

    @Override
    public PagingAndSortingRepository<WalletBase, Integer> getDao() {
        return dao;
    }

    @Override
    public WalletBase getEntity() {
        return new WalletBase();
    }

    @Override
    public boolean checkDataValidity(WalletBase o) {
        return true;
    }

    public WalletBase getUserWallet(Integer userId) {
        return dao.getUserWallet(userId);
    }
}
