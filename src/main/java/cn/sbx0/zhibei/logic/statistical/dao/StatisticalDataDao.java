package cn.sbx0.zhibei.logic.statistical.dao;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatisticalDataDao extends PagingAndSortingRepository<StatisticalData, Integer> {
    @Query(value = "select * from data where name = ?1", nativeQuery = true)
    StatisticalData findByName(String name);
}
