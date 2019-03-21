package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.AnswerDao;
import cn.sbx0.zhibei.entity.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 问题 服务层
 */
@Service
public class AnswerService extends BaseService<Answer, Integer> {
    @Resource
    private AnswerDao answerDao;

    @Override
    public PagingAndSortingRepository<Answer, Integer> getDao() {
        return answerDao;
    }

    @Override
    public Answer getEntity() {
        return new Answer();
    }

}
