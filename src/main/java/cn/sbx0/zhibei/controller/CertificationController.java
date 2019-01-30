package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Certification;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.CertificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 认证 控制层
 */
@Controller
@RequestMapping("/certification")
public class CertificationController extends BaseController<Certification, Integer> {
    @Resource
    private CertificationService certificationService;

    @Override
    public BaseService<Certification, Integer> getService() {
        return certificationService;
    }

    @Autowired
    public CertificationController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
