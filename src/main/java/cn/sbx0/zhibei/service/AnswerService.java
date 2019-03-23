package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.AnswerDao;
import cn.sbx0.zhibei.dao.QuestionDao;
import cn.sbx0.zhibei.entity.Answer;
import cn.sbx0.zhibei.entity.Question;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * 问题 服务层
 */
@Service
public class AnswerService extends BaseService<Answer, Integer> {
    @Resource
    private AnswerDao answerDao;
    @Resource
    private QuestionDao questionDao;

    @Override
    public PagingAndSortingRepository<Answer, Integer> getDao() {
        return answerDao;
    }

    @Override
    public Answer getEntity() {
        return new Answer();
    }

    /**
     * 判断该用户是否已经回答过该问题了
     *
     * @param p_id
     * @return
     */
    public boolean existsByQuestionAndAnswerer(Integer p_id, Integer u_id) {
        String result = answerDao.existsByQuestionAndAnswerer(p_id, u_id);
        return result != null;
    }

    /**
     * 发布回答
     *
     * @param answer
     * @param user
     * @return
     */
    public boolean post(Answer answer, Integer q_id, User user) {
        if (user == null) return false; // 用户为空
        if (StringTools.checkNullStr(answer.getContent())) return false; // 内容为空
        Optional<Question> question = questionDao.findById(q_id);
        if (!question.isPresent()) return false; // 问题不存在
        Question q = question.get();
        // 该问题为指定用户回答 而回答用户不是指定用户
        if (q.getAppoint() != null && !q.getAppoint().getId().equals(user.getId())) {
            return false;
        }
        // 防止插入恶意代码
        answer.setContent(StringTools.killHTML(answer.getContent()));
        answer.setTime(new Date());
        answer.setDislikes(0);
        answer.setLikes(0);
        answer.setAnswerer(user);
        answer.setQuestion(q);
        return save(answer);
    }

    /**
     * 根据问题查找回答
     *
     * @param q_id
     * @param pageable
     * @return
     */
    public Page<Answer> findByQuestion(Integer q_id, Pageable pageable) {
        return answerDao.findByQuestion(q_id, pageable);
    }

    /**
     * 根据用户查询回答
     *
     * @param u_id
     * @param pageable
     * @return
     */
    public Page<Answer> findByAnswer(Integer u_id, Pageable pageable) {
        return answerDao.findByAnswerer(u_id, pageable);
    }
}
