package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Question;
import cn.sbx0.zhibei.entity.Record;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.service.*;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 记录 控制层
 */
@Controller
@RequestMapping("/record")
public class RecordController extends BaseController<Record, Integer> {
    @Resource
    private RecordService recordService;
    @Resource
    private WalletService walletService;
    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    @Override
    public BaseService<Record, Integer> getService() {
        return recordService;
    }

    @Autowired
    public RecordController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 检查记录
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/check")
    public ObjectNode check(String url) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (StringTools.checkNullStr(url)) {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
                return json;
            }
            String path = url.substring(1);
            String type = path.split("/")[0];
            Integer id;
            Question question = null;
            if (type.equals("question")) {
                id = Integer.parseInt(path.split("/")[1]);
                question = questionService.findById(id);
                if (question == null) {
                    json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
                    return json;
                }
            }
            if (question.getAppoint() == null || (question.getAppoint() != null && question.getAppoint().getId().equals(user.getId())) || question.getQuizzer().getId().equals(user.getId())) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                return json;
            }
            Record record = recordService.findByPathAndUser(url, user.getId());
            if (record != null) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 付款添加记录
     *
     * @param url
     * @param money
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/pay")
    public ObjectNode pay(String url, double money) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            String path = url.substring(1);
            String type = path.split("/")[0];
            Integer id;
            Question question = null;
            if (type.equals("question")) {
                id = Integer.parseInt(path.split("/")[1]);
                question = questionService.findById(id);
                if (question == null) {
                    json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
                    return json;
                }
            }
            Record oldRecord = recordService.findByPathAndUser(url, user.getId());
            if (oldRecord != null) {
                json.put(STATUS_NAME, STATUS_CODE_REPEAT);
                return json;
            }
            if (StringTools.checkNullStr(url)) {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
                return json;
            }
            if (money <= 0) {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
                return json;
            }
            Record record = new Record();
            record.setCost(money);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Wallet userWallet = walletService.getUserWallet(user);
            Wallet appointWallet = walletService.getUserWallet(question.getAppoint());
            Wallet quizzerWallet = walletService.getUserWallet(question.getQuizzer());
            Wallet adminWallet = walletService.getUserWallet(userService.findByName("admin"));
            appointWallet.setMoney(appointWallet.getMoney() + (money * 0.5));
            if (walletService.save(appointWallet)) {
                if (!messageService.sendNotice("系统通知：您已于 " + sdf.format(new Date()) + " 收到付费问答 <" + question.getTitle() + "> 的参与奖励 " + (money * 0.5) + "￥，账户余额" + (int) (appointWallet.getMoney() * 100) / 100.0 + "￥。", question.getAppoint())) {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                    return json;
                }
            }
            quizzerWallet.setMoney(quizzerWallet.getMoney() + (money * 0.4));
            if (walletService.save(quizzerWallet)) {
                if (!messageService.sendNotice("系统通知：您已于 " + sdf.format(new Date()) + " 收到付费问答 <" + question.getTitle() + "> 的参与奖励 " + (money * 0.4) + "￥，账户余额" + (int) (quizzerWallet.getMoney() * 100) / 100.0 + "￥。", question.getQuizzer())) {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                    return json;
                }
            }
            adminWallet.setMoney(adminWallet.getMoney() + (money * 0.1));
            if (!walletService.save(adminWallet)) {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
                return json;
            }
            if (userWallet.getMoney() < money) {
                json.put(STATUS_NAME, STATUS_CODE_INSUFFICIENT_BALANCE);
                return json;
            }
            userWallet.setMoney(userWallet.getMoney() - money);
            if (walletService.save(userWallet)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
            record.setBalance(userWallet.getMoney());
            record.setUser(user);
            record.setTime(new Date());
            record.setPath(url);
            if (recordService.save(record)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

}
