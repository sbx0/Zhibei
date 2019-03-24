package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.QuestionDao;
import cn.sbx0.zhibei.dao.TagDao;
import cn.sbx0.zhibei.entity.Question;
import cn.sbx0.zhibei.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 问题 服务层
 */
@Service
public class QuestionService extends BaseService<Question, Integer> {
    @Resource
    private QuestionDao questionDao;
    @Resource
    private TagDao tagDao;

    @Override
    public PagingAndSortingRepository<Question, Integer> getDao() {
        return questionDao;
    }

    @Override
    public Question getEntity() {
        return new Question();
    }

    /**
     * 根据用户查询问题
     *
     * @param u_id
     * @param pageable
     * @return
     */
    public Page<Question> findByQuizzer(Integer u_id, Pageable pageable) {
        return questionDao.findByQuizzer(u_id, pageable);
    }

    /**
     * 根据标签查找问题
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<Question> findByTag(Integer id, Pageable pageable) {
        return questionDao.findByTag(id, pageable);
    }
}
