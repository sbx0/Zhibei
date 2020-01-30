package cn.sbx0.zhibei.logic.statistical.controller;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import cn.sbx0.zhibei.logic.statistical.service.StatisticalDataService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/statistical/data")
public class StatisticalDataController extends BaseController<StatisticalData, Integer> {
    @Resource
    private StatisticalDataService service;

    @Override
    public BaseService<StatisticalData, Integer> getService() {
        return service;
    }

    /**
     * 最近的数据
     *
     * @param day   day
     * @param kind  kind
     * @param group group
     * @return ObjectNode
     */
    @GetMapping(value = "/recent")
    public ObjectNode recent(Integer day, String kind, String group) {
        ObjectNode json = initJSON();
        ObjectNode data = service.findByDay(day, kind, group);
        json.set("objects", data);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
