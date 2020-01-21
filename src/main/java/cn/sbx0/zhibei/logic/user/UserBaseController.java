package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 基础用户 控制层
 */
@RestController
@RequestMapping("/userBase")
public class UserBaseController extends BaseController<UserBase, Integer> {
    @Resource
    private UserBaseService service;

    /**
     * 注册
     *
     * @param user 页面发来的数据
     * @return 返回json
     */
    @PostMapping("/register")
    public ObjectNode register(UserBaseView user) {
        json = mapper.createObjectNode();
        int status = service.register(user);
        json.put(statusCode, status);
        return json;
    }

    @Override
    public BaseService<UserBase, Integer> getService() {
        return service;
    }

    @Autowired
    public UserBaseController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
