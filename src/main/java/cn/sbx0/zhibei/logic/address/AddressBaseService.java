package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressBaseService extends BaseService<AddressBase, Integer> {
    @Resource
    private AddressBaseDao dao;

    @Override
    public PagingAndSortingRepository<AddressBase, Integer> getDao() {
        return dao;
    }

    @Override
    public AddressBase getEntity() {
        return new AddressBase();
    }

    @Override
    public boolean checkDataValidity(AddressBase AddressBase) {
        return true;
    }

    public List<AddressBase> findAllFather() {
        return dao.findAllFather();
    }

    public List<AddressBase> findAllSon(Integer fatherId) {
        return dao.findAllSon(fatherId);
    }
}
