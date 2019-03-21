package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Question;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionDao extends PagingAndSortingRepository<Question, Integer> {
}
