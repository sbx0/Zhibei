package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Certification;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.CertificationService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/status")
    public ObjectNode status() {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Normal.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            Certification certification = certificationService.findByUserAndPassed(user.getId());
            if (certification != null) {
                Date date = new Date();
                if (certification.getEndTime() != null && certification.getEndTime().getTime() < date.getTime()) {
                    certificationService.delete(certification);
                    certification.setPassed(false);
                }
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
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/submit")
    public ObjectNode submit(Certification certification) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (certificationService.existsByUserAndPassed(user.getId())) {
                json.put(STATUS_NAME, STATUS_CODE_REPEAT);
            } else {
                certification.setPassed(null);
                certification.setUser(user);
                certification.setStartTime(new Date());
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
