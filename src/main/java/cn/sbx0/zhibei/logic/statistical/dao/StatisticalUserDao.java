package cn.sbx0.zhibei.logic.statistical.dao;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface StatisticalUserDao extends PagingAndSortingRepository<StatisticalUser, Integer> {
    @Query(value = "select * from statistical_user where ip = ?1 and client = ?2 and time > ?3 order by id desc limit 1", nativeQuery = true)
    StatisticalUser findByIpAndTime(String ip, String client, Date date);

    @Query(value = "select count(*) from statistical_user where time > ?1", nativeQuery = true)
    int countByTime(Date time);

    @Query(value = "select distinct(u.client) from statistical_user as u", nativeQuery = true)
    List<String> findAllClient();

    @Query(value = "select count(*) from statistical_user as u where u.client = ?1", nativeQuery = true)
    int countByClient(String client);
}