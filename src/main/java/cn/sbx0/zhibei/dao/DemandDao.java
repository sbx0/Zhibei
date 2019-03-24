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

    /**
     * 根据标签查询需求
     *
     * @param t_id
     * @param pageable
     * @return
     */
    @Query(value = "select * from (select * from demands d1 where d1.id in (select dt1.demand_id from demands_tags dt1 where dt1.tags_id = ?1) union select * from demands d2 where d2.id in (select dt2.demand_id from demands_tags dt2 where dt2.tags_id in (select t2.id from tags t2 where t2.father_id = ?1))) as d order by d.time", nativeQuery = true)
    Page<Demand> findByTag(Integer t_id, Pageable pageable);
}