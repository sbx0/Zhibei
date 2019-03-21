package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Answer;
import cn.sbx0.zhibei.service.AnswerService;
import cn.sbx0.zhibei.service.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 问题 控制层
 */
@Controller
@RequestMapping(value = "/answer")
public class AnswerController extends BaseController<Answer, Integer> {
    @Resource
    private AnswerService answerService;

    @Override
    public BaseService<Answer, Integer> getService() {
        return answerService;
    }

    @Autowired
    public AnswerController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
