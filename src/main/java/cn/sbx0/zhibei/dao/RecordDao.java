package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 记录 数据层
 */
public interface RecordDao extends PagingAndSortingRepository<Record, Integer> {
    /**
     * 根据页面和用户查找记录
     *
     * @param path
     * @param u_id
     * @return
     */
    @Query(value = "FROM Record r WHERE r.path = ?1 AND r.user.id = ?2")
    Record findByPathAndUser(String path, Integer u_id);
}
