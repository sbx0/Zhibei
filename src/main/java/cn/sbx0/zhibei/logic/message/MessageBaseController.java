package cn.sbx0.zhibei.logic.message;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/message/base")
public class MessageBaseController extends BaseController<MessageBase, Integer> {
    @Resource
    private MessageBaseService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<MessageBase, Integer> getService() {
        return service;
    }

    @LoginRequired
    @GetMapping(value = "/receive")
    public ObjectNode receive(int page, int size) {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        List<MessageBase> messageBaseList = service.receive(userId, page, size);
        return json;
    }

    @LoginRequired
    @PostMapping(value = "/send")
    public ObjectNode send(MessageBase message) {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        if (service.checkDataValidity(message)) {
            message.setSendTime(new Date());
            message.setReceiveTime(null);
            message.setSendUserId(userId);
            message.setType("msg");
            service.save(message);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    @LoginRequired
    @PostMapping(value = "/notice")
    public ObjectNode notice() {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        int count = service.notice(userId);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @LoginRequired
    @GetMapping(value = "/read")
    public ObjectNode read(Integer msgId) {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        ReturnStatus status = service.read(msgId, userId);
        json.put(statusCode, status.getCode());
        return json;
    }
}

