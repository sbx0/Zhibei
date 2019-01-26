package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Specialist;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 专家 数据层
 */
public interface SpecialistDao extends PagingAndSortingRepository<Specialist, Integer> {

}