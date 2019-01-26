package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Enterprise;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 企业 数据层
 */
public interface EnterpriseDao extends PagingAndSortingRepository<Enterprise, Integer> {

}