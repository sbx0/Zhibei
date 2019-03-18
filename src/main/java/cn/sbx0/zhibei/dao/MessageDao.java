package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

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
    @Query(value = "FROM Message m WHERE ((m.sendUser.id = ?1 AND m.receiveUser.id = ?2) OR (m.sendUser.id = ?2 AND m.receiveUser.id = ?1)) AND m.type = 'msg'")
    Page<Message> findBySenderAndReceiver(Integer sendUserId, Integer receiverId, Pageable pageable);

    /**
     * 某时间段的消息
     */
    @Query(value = "FROM Message m WHERE m.sendTime > ?1 AND m.sendTime < ?2 AND m.type = ?3 AND m.type = 'msg'")
    List<Message> findByTime(Date begin, Date end, String type);

    /**
     * 获取消息
     *
     * @param id
     * @param buildPageable
     * @return
     */
    @Query(value = "FROM Message m WHERE (m.receiveUser.id = ?1 AND m.type = 'msg' AND m.receiveTime = null) OR (m.type = 'notice')")
    Page<Message> findByReceiver(Integer id, Pageable buildPageable);

    /**
     * 消息计数
     *
     * @param id
     * @return
     */
    @Query(value = "SELECT COUNT (m) FROM Message m WHERE (m.receiveUser.id = ?1 AND m.type = 'msg' AND m.receiveTime = null)")
    Integer countByReceiver(Integer id);

    /**
     * 已读某人的全部信息
     *
     * @param sendUserId
     * @param receiveUserId
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE Message m SET m.receiveTime = CURRENT_DATE WHERE m.sendUser.id = ?1 AND m.receiveUser.id = ?2 AND m.receiveTime = null")
    void readByUser(Integer sendUserId, Integer receiveUserId);
}