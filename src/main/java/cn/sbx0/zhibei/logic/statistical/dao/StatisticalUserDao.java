package cn.sbx0.zhibei.logic.statistical.dao;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StatisticalUserDao extends PagingAndSortingRepository<StatisticalUser, Integer> {
    @Query(value = "select * from data where name = ?1", nativeQuery = true)
    StatisticalData findByName(String name);
}
