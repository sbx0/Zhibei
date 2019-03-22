package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionDao extends PagingAndSortingRepository<Question, Integer> {
    /**
     * 根据用户查询问题
     *
     * @param u_id
     * @param pageable
     * @return
     */
    @Query(value = "FROM Question q WHERE q.quizzer.id = ?1")
    Page<Question> findByQuizzer(Integer u_id, Pageable pageable);
}
