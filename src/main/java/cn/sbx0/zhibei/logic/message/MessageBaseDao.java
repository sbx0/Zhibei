package cn.sbx0.zhibei.logic.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageBaseDao extends PagingAndSortingRepository<MessageBase, Integer> {

}
