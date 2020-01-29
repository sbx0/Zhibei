package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息 控制层
 */
@RestController
@RequestMapping("/user/info")
public class UserInfoController extends BaseController<UserInfo, Integer> {
    @Resource
    private UserInfoService service;

    @Override
    public BaseService<UserInfo, Integer> getService() {
        return service;
    }

}
