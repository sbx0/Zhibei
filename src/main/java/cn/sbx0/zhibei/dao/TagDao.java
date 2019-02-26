package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 标签 数据层
 */
public interface TagDao extends PagingAndSortingRepository<Tag, Integer> {
    /**
     * 根据名称查询
     *
     * @param name
     * @param pageable
     * @return
     */
    @Query(value = "FROM Tag t WHERE t.name LIKE ?1")
    Page<Tag> findByName(String name, Pageable pageable);
}
