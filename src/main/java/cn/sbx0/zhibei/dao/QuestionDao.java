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

    /**
     * 根据标签查询问题
     *
     * @param t_id
     * @param pageable
     * @return
     */
    @Query(value = "select * from questions q1 where q1.id in (select qt1.question_id from questions_tags qt1 where qt1.tags_id = ?1) union select * from questions q2 where q2.id in (select qt2.question_id from questions_tags qt2 where qt2.tags_id in (select t2.id from tags t2 where t2.father_id = ?1))", nativeQuery = true)
    Page<Question> findByTag(Integer t_id, Pageable pageable);
}
