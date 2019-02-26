package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Demand;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.DemandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 需求 控制层
 */
@Controller
@RequestMapping("/demand")
public class DemandController extends BaseController<Demand, Integer> {
    @Resource
    private DemandService demandService;

    @Override
    public BaseService<Demand, Integer> getService() {
        return demandService;
    }

    @Autowired
    public DemandController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
