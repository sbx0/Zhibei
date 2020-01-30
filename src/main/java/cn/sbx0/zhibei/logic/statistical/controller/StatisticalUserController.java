package cn.sbx0.zhibei.logic.statistical.controller;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import cn.sbx0.zhibei.logic.statistical.service.StatisticalUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/statistical/user")
public class StatisticalUserController extends BaseController<StatisticalUser, Integer> {
    @Resource
    private StatisticalUserService service;

    @Override
    public BaseService<StatisticalUser, Integer> getService() {
        return service;
    }
}
