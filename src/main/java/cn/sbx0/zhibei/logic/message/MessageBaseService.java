package cn.sbx0.zhibei.logic.message;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageBaseService extends BaseService<MessageBase, Integer> {
    @Resource
    private MessageBaseDao dao;

    @Override
    public PagingAndSortingRepository<MessageBase, Integer> getDao() {
        return dao;
    }

    @Override
    public MessageBase getEntity() {
        return new MessageBase();
    }

    @Override
    public boolean checkDataValidity(MessageBase o) {
        return true;
    }
}
