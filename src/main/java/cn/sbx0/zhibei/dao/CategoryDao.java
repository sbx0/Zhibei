package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 分类 数据层
 */
public interface CategoryDao extends PagingAndSortingRepository<Category, Integer> {
    /**
     * 根据名称查询
     *
     * @param name
     * @param pageable
     * @return
     */
    @Query(value = "FROM Category c WHERE c.name LIKE ?1")
    Page<Category> findByName(String name, Pageable pageable);
}
