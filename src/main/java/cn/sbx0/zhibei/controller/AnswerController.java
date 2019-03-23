package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.*;
import cn.sbx0.zhibei.service.*;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 问题 控制层
 */
@Controller
@RequestMapping(value = "/answer")
public class AnswerController extends BaseController<Answer, Integer> {
    @Resource
    private AnswerService answerService;
    @Resource
    private QuestionService questionService;
    @Resource
    private WalletService walletService;
    @Resource
    private MessageService messageService;

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
            // 同一问题只能回答一次
            if (answerService.existsByQuestionAndAnswerer(q_id, user.getId())) {
                json.put(STATUS_NAME, STATUS_CODE_REPEAT);
                return json;
            }
            Question question = questionService.findById(q_id);
            // 指定用户问题只能指定用户回答
            if (question.getAppoint() != null && !question.getAppoint().getId().equals(user.getId())) {
                json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
                return json;
            }
            // 指定用户回答即可获得奖励
            if (question.getAppoint() != null && question.getAppoint().getId().equals(user.getId())) {
                Wallet wallet = walletService.getUserWallet(question.getAppoint());
                wallet.setMoney(wallet.getMoney() + question.getPrice());
                answer.setTop(1);
                if (walletService.save(wallet)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    if (messageService.sendNotice("系统通知：您已于 " + sdf.format(new Date()) + " 收到付费问答 <" + question.getTitle() + "> 的回答奖励 " + question.getPrice() + "￥，账户余额" + (int) (wallet.getMoney() * 100) / 100.0 + "￥。", question.getAppoint())) {
                        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_FILED);
                        return json;
                    }
                }
            } else {
                answer.setTop(0);
            }
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
