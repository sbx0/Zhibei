package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.WalletDao;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Wallet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WalletService extends BaseService<Wallet, Integer> {
    @Resource
    private WalletDao walletDao;

    @Override
    public PagingAndSortingRepository<Wallet, Integer> getDao() {
        return walletDao;
    }

    @Override
    public Wallet getEntity() {
        return new Wallet();
    }

    /**
     * 查找某用户的钱包数据
     *
     * @param user
     * @return
     */
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletDao.findByUser(user.getId());
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setOwner(user);
            wallet.setMoney(0.0);
        }
        return wallet;
    }
}
