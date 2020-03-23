package cn.sbx0.zhibei.logic.message;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseDao;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MessageBaseService extends BaseService<MessageBase, Integer> {
    @Resource
    private MessageBaseDao dao;
    @Resource
    private UserBaseDao userBaseDao;
    @Resource
    private MessageBaseMapper messageBaseMapper;

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

    public int notice(Integer userId) {
        return messageBaseMapper.countYourMsg(userId, "id", "DESC");
    }

    public void notification(String msg, String link, Integer userId) {
        MessageBase messageBase = new MessageBase();
        messageBase.setReceiveUserId(userId);
        UserBase userBase = userBaseDao.findByName("智贝BOT");
        messageBase.setSendTime(new Date());
        messageBase.setContent(msg);
        messageBase.setLink(link);
        messageBase.setType("notification");
        dao.save(messageBase);
    }

    public List<MessageBase> findBroadcast() {
        return dao.findBroadcast();
    }


    @Transactional
    public ReturnStatus read(Integer msgId, Integer userId) {
        Integer msg = dao.findByIdAndUserId(msgId, userId);
        if (msg != null) return ReturnStatus.failed;
        dao.read(msgId);
        return ReturnStatus.success;
    }

    public List<MessageBase> receive(Integer userId, Integer page, Integer size) {
        int total = messageBaseMapper.countYourMsg(userId, "id", "DESC");
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        int begin = (page - 1) * size;
        if (begin > total) begin = total;
        return messageBaseMapper.findYourMsg(userId, "id", "DESC", begin, size);
    }
}
