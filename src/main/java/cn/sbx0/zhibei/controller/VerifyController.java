package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Verify;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.VerifyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/verify")
public class VerifyController extends BaseController<Verify, Integer> {
    @Resource
    private VerifyService verifyService;

    @Override
    public BaseService<Verify, Integer> getService() {
        return verifyService;
    }

    @Autowired
    public VerifyController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
