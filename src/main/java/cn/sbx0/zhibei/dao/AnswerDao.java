package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 问题 数据层
 */
public interface AnswerDao extends PagingAndSortingRepository<Answer, Integer> {
    /**
     * 根据问题查询回答
     *
     * @param q_id
     * @param pageable
     * @return
     */
    @Query(value = "FROM Answer a WHERE a.question.id = ?1")
    Page<Answer> findByQuestion(Integer q_id, Pageable pageable);

    /**
     * 根据用户查询回答
     *
     * @param u_id
     * @param pageable
     * @return
     */
    @Query(value = "FROM Answer a WHERE a.answerer.id = ?1")
    Page<Answer> findByAnswerer(Integer u_id, Pageable pageable);
}
