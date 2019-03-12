package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * 消息 数据层
 */
public interface MessageDao extends PagingAndSortingRepository<Message, Integer> {

    /**
     * 某时间段的消息
     */
    @Query(value = "SELECT m FROM Message m WHERE m.sendTime > ?1 AND m.sendTime < ?2 AND m.type = ?3")
    List<Message> findByTime(Date begin, Date end, String type);

}