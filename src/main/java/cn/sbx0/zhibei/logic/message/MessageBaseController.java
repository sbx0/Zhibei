package cn.sbx0.zhibei.logic.message;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message/base")
public class MessageBaseController extends BaseController<MessageBase, Integer> {
    @Resource
    private MessageBaseService service;

    @Override
    public BaseService<MessageBase, Integer> getService() {
        return service;
    }
}

