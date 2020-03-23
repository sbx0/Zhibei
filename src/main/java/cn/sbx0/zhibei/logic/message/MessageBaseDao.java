package cn.sbx0.zhibei.logic.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageBaseDao extends PagingAndSortingRepository<MessageBase, Integer> {

    @Query(value = "select 1 from message_base where id = ?1 and send_user_id = ?2", nativeQuery = true)
    int findByIdAndUserId(Integer msgId, Integer userId);

    @Query(value = "update message_base set receieve_time = now() where id = ?1 and receieve_time is null", nativeQuery = true)
    void read(Integer msgId);
}
