package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.University;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 角色 数据层
 */
public interface UniversityDao extends PagingAndSortingRepository<University, Integer> {

}