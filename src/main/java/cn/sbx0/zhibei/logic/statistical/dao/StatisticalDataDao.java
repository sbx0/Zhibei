package cn.sbx0.zhibei.logic.statistical.dao;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StatisticalDataDao extends PagingAndSortingRepository<StatisticalData, Integer> {
    @Query(value = "select * from statistical_data as d where to_days(NOW()) - to_days(record_time) = ?1 and d.type = ?2 and d.group_by = ?3 order by d.value desc limit 1", nativeQuery = true)
    StatisticalData findByDay(int i, String kind, String group);

    @Query(value = "select * from statistical_data as d where to_days(NOW()) - to_days(record_time) < ?1 and d.type = ?2 and d.group_by = ?3 order by d.record_time", nativeQuery = true)
    List<StatisticalData> findByKindAndGrouping(int i, String kind, String group);
}
