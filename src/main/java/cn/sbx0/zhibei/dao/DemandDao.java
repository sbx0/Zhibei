package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Demand;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 需求 数据层
 */
public interface DemandDao extends PagingAndSortingRepository<Demand, Integer> {

}
