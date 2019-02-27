package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 需求 数据层
 */
public interface DemandDao extends PagingAndSortingRepository<Demand, Integer> {

    /**
     * 根据用户名查询需求
     *
     * @param id
     * @param buildPageable
     * @return
     */
    @Query(value = "FROM Demand d WHERE d.poster.id = ?1")
    Page<Demand> findByPoster(Integer id, Pageable buildPageable);
}
