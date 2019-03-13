package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.Message;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.MessageService;
import cn.sbx0.zhibei.tool.RequestTools;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController<Message, Integer> {
    @Resource
    private MessageService messageService;

    @Override
    public BaseService<Message, Integer> getService() {
        return messageService;
    }

    @Autowired
    public MessageController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping(value = "/send")
    public ObjectNode send(Message message) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        if (message.getReceiveUser() == null) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        if (message.getReceiveUser().getId().equals(user.getId())) {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
            return json;
        }
        if (StringTools.checkNullStr(message.getContent())) {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
            return json;
        }
        message.setId(null);
        message.setSendTime(new Date());
        message.setIp(RequestTools.getIpAddress());
        message.setSendUser(user);
        message.setType("msg");
        if (messageService.save(message)) {
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    @LogRecord
    @ResponseBody
    @GetMapping(value = "/receive")
    public ObjectNode receive(Integer id, Integer page, Integer size) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Normal.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Page<Message> tPage = messageService.findBySenderAndReceiver(user.getId(), id, BaseService.buildPageable(1, 1000, "id", "ASC"));
        List<Message> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Message t : tList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
            json.put("user_id", user.getId());
            json.put("total_pages", tPage.getTotalPages());
            json.put("total_elements", tPage.getTotalElements());
            json.put("page", page);
            json.put("size", size);
        }
        return json;
    }

}
