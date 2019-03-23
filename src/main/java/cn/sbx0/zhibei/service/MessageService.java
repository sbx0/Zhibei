package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.MessageDao;
import cn.sbx0.zhibei.entity.Message;
import cn.sbx0.zhibei.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 消息 服务层
 */
@Service
public class MessageService extends BaseService<Message, Integer> {
    @Resource
    private MessageDao messageDao;
    @Resource
    private UserService userService;

    @Override
    public PagingAndSortingRepository<Message, Integer> getDao() {
        return messageDao;
    }

    @Override
    public Message getEntity() {
        return new Message();
    }

    /**
     * 向用户发送通知
     *
     * @param msg
     * @param user
     * @return
     */
    @Transactional
    public boolean sendNotice(String msg, User user) {
        Message message = new Message();
        message.setType("msg");
        message.setSendTime(new Date());
        message.setIp("0.0.0.0");
        message.setContent(msg);
        message.setSendUser(userService.findByName("admin"));
        message.setReceiveUser(user);
        try {
            messageDao.save(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
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

    /**
     * 已读某用户的全部消息
     *
     * @param sendUserId
     * @param receiveUserId
     */
    public void readByUser(Integer sendUserId, Integer receiveUserId) {
        messageDao.readByUser(sendUserId, receiveUserId);
    }
}
