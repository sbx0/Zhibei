package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.MessageDao;
import cn.sbx0.zhibei.entity.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageService extends BaseService<Message, Integer> {
    @Resource
    private MessageDao messageDao;

    @Override
    public PagingAndSortingRepository<Message, Integer> getDao() {
        return messageDao;
    }

    @Override
    public Message getEntity() {
        return new Message();
    }
}
