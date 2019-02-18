package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Certification;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.CertificationService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    /**
     * 获取当前申请状态
     *
     * @param request
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/status")
    public ObjectNode status(HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(request);
        if (user != null) {
            Certification certification = certificationService.findByUserAndPassed(user.getId());
            if (certification != null) {
                ObjectNode object = mapper.convertValue(certification, ObjectNode.class);
                json.set("certification", object);
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 提交申请
     *
     * @param certification
     * @param request
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/submit")
    public ObjectNode submit(Certification certification, HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(request);
        if (user != null) {
            if (certificationService.existsByUserAndPassed(user.getId())) {
                json.put(STATUS_NAME, STATUS_CODE_REPEAT);
            } else {
                certification.setPassed(null);
                certification.setImg("sdfasdf");
                certification.setUser(user);
                certification.setStart_time(new Date());
                if (certificationService.save(certification)) {
                    json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                }
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 申请列表
     */

    /**
     * 通过申请 / 驳回申请
     */

}
