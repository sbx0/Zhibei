package cn.sbx0.zhibei.logic.statistical.dao;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatisticalDataDao extends PagingAndSortingRepository<StatisticalData, Integer> {
    @Query(value = "select * from statistical_data as d where to_days(NOW()) - to_days(time) = ?1 and d.kind = ?2 and d.grouping = ?3 order by d.value desc limit 1", nativeQuery = true)
    StatisticalData findByDay(int i, String kind, String group);
}
