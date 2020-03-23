package cn.sbx0.zhibei.logic.message;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageBaseService extends BaseService<MessageBase, Integer> {
    @Resource
    private MessageBaseDao dao;
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

    @Transactional
    public ReturnStatus read(Integer msgId, Integer userId) {
        int msg = dao.findByIdAndUserId(msgId, userId);
        if (msg != 1) return ReturnStatus.failed;
        dao.read(msgId);
        return ReturnStatus.success;
    }

    public List<MessageBase> receive(Integer userId, Integer page, Integer size) {
        int total = messageBaseMapper.countYourMsg(userId, "id", "DESC");
        if (page == null || page < 1) return null;
        if (size == null || size < 1) return null;
        int begin = (page - 1) * size;
        if (begin > total) begin = total;
        return messageBaseMapper.findYourMsg(userId, "id", "DESC", begin, size);
    }
}
