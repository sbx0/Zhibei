package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Answer;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.AnswerService;
import cn.sbx0.zhibei.service.BaseService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 回答问题
     *
     * @param answer
     * @param q_id
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Answer answer, Integer q_id) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (answerService.post(answer, q_id, user)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 根据问题查询回答
     *
     * @param id
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/load")
    public ObjectNode load(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Answer> tPage = answerService.findByQuestion(id, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Answer> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Answer c : tList) {
                ObjectNode object = mapper.convertValue(c, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

    /**
     * 根据用户查询回答
     *
     * @param id
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/load/user")
    public ObjectNode loadByUser(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Answer> tPage = answerService.findByAnswer(id, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Answer> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Answer c : tList) {
                ObjectNode object = mapper.convertValue(c, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

}
