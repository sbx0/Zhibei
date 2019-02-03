package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 日志数据层
 */
public interface LogDao extends PagingAndSortingRepository<Log, Integer> {

}
