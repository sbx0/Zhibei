package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 问题 数据层
 */
public interface AnswerDao extends PagingAndSortingRepository<Answer, Integer> {
}
