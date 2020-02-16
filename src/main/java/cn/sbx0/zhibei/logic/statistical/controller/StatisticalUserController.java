package cn.sbx0.zhibei.logic.statistical.controller;

import cn.sbx0.zhibei.annotation.RoleCheck;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import cn.sbx0.zhibei.logic.statistical.service.StatisticalUserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 查询最近的客户端统计数据
     *
     * @return ObjectNode
     */
    @GetMapping(value = "/client")
    public ObjectNode client() {
        ObjectNode json = initJSON();
        ObjectNode data = service.countByClient();
        json.set("objects", data);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
