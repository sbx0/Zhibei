package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.MessageDao;
import cn.sbx0.zhibei.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 查询消息
     *
     * @param sendUserId
     * @param receiverId
     * @param pageable
     * @return
     */
    public Page<Message> findBySenderAndReceiver(Integer sendUserId, Integer receiverId, Pageable pageable) {
        return messageDao.findBySenderAndReceiver(sendUserId, receiverId, pageable);
    }

    /**
     * 获取消息
     *
     * @param id
     * @param buildPageable
     * @return
     */
    public Page<Message> findByReceiver(Integer id, Pageable buildPageable) {
        return messageDao.findByReceiver(id, buildPageable);
    }

    /**
     * 获取消息
     *
     * @param id
     * @return
     */
    public Integer countByReceiver(Integer id) {
        Integer count = messageDao.countByReceiver(id);
        if (count != null) return count;
        else return 0;
    }
}
