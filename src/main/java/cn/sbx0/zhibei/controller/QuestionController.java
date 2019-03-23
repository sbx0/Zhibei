package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.Question;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.MessageService;
import cn.sbx0.zhibei.service.QuestionService;
import cn.sbx0.zhibei.service.WalletService;
import cn.sbx0.zhibei.tool.StringTools;
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
@RequestMapping(value = "/question")
public class QuestionController extends BaseController<Question, Integer> {
    @Resource
    private QuestionService questionService;
    @Resource
    private WalletService walletService;
    @Resource
    private MessageService messageService;


    @Override
    public BaseService<Question, Integer> getService() {
        return questionService;
    }

    @Autowired
    public QuestionController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 发布问题
     *
     * @param question
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Question question) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        question.setId(null);
        if (!StringTools.checkNullStr(question.getTitle())
                && !StringTools.checkNullStr(question.getDescription())
        ) {
            question.setTitle(StringTools.killHTML(question.getTitle().trim()));
            question.setDescription(question.getDescription().trim());
            question.setTime(new Date());
            question.setQuizzer(user);
            if (question.getPrice() != null) {
                if (question.getPrice() < 0) {
                    question.setPrice(0.00);
                } else if (question.getPrice() > 0) {
                    Wallet wallet = walletService.getUserWallet(user);
                    if (wallet.getMoney() > question.getPrice()) {
                        wallet.setMoney(wallet.getMoney() - question.getPrice());
                        if (walletService.save(wallet)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                            if (messageService.sendNotice("系统通知：您于 " + sdf.format(question.getTime()) + " 花费" + question.getPrice() + "￥发布一篇付费提问，系统自动扣款，余额" + wallet.getMoney() + "￥，如有问题请回复。", user)) {
                                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                            } else {
                                json.put(STATUS_NAME, STATUS_CODE_FILED);
                            }
                        } else {
                            json.put(STATUS_NAME, STATUS_CODE_FILED);
                        }
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_INSUFFICIENT_BALANCE);
                        return json;
                    }
                }
            }
            if (question.getAppoint() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                if (messageService.sendNotice("系统通知：您于 " + sdf.format(question.getTime()) + " 收到一篇付费问答 <" + question.getTitle() + "> ，回答即可获得 " + question.getPrice() + "￥。", question.getAppoint())) {
                    json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                    return json;
                }
            }
            json = add(question);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    /**
     * 用户页获取问题
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/user")
    public ObjectNode user(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Question> tPage = questionService.findByQuizzer(id, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Question> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Question t : tList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }
}
