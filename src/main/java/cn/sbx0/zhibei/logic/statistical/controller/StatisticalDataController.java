package cn.sbx0.zhibei.logic.statistical.controller;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import cn.sbx0.zhibei.logic.statistical.service.StatisticalDataService;
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
}
