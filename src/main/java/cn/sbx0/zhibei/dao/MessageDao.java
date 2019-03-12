package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * 消息 数据层
 */
public interface MessageDao extends PagingAndSortingRepository<Message, Integer> {

    /**
     * 查询消息
     *
     * @param sendUserId
     * @param receiverId
     * @param pageable
     * @return
     */
    @Query(value = "FROM Message m WHERE m.sendUser.id = ?1 AND m.receiveUser.id = ?2")
    Page<Message> findBySenderAndReceiver(Integer sendUserId, Integer receiverId, Pageable pageable);

    /**
     * 某时间段的消息
     */
    @Query(value = "FROM Message WHERE sendTime > ?1 AND sendTime < ?2 AND type = ?3")
    List<Message> findByTime(Date begin, Date end, String type);

}