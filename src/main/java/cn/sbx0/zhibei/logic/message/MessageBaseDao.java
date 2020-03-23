package cn.sbx0.zhibei.logic.message;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageBaseDao extends PagingAndSortingRepository<MessageBase, Integer> {

    @Query(value = "select * from message_base where type = 'broadcast' and receive_time > now()", nativeQuery = true)
    List<MessageBase> findBroadcast();

    @Query(value = "select 1 from message_base where id = ?1 and send_user_id = ?2 limit 0,1", nativeQuery = true)
    Integer findByIdAndUserId(Integer msgId, Integer userId);

    @Modifying
    @Query(value = "update message_base set receive_time = now() where id = ?1 and receive_time is null", nativeQuery = true)
    void read(Integer msgId);
}
