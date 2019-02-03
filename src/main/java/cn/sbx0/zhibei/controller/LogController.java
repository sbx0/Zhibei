package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Log;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 日志 控制层
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController<Log, Integer> {
    @Resource
    private LogService logService;

    @Override
    public BaseService<Log, Integer> getService() {
        return logService;
    }

    @Autowired
    public LogController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
