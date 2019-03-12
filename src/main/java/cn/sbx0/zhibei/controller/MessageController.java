package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Message;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

}
