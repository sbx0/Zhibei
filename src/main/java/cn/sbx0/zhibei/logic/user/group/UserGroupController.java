package cn.sbx0.zhibei.logic.user.group;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户组 控制层
 */
@RestController
@RequestMapping("/user/group")
public class UserGroupController extends BaseController<UserGroup, Integer> {
    @Resource
    private UserGroupService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<UserGroup, Integer> getService() {
        return service;
    }

    @LoginRequired
    @GetMapping("/check")
    public ObjectNode check(Integer id) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.checkUser(id, userBaseService.getLoginUserId());
        json.put(statusCode, status.getCode());
        return json;
    }

    @LoginRequired
    @GetMapping("/quit")
    public ObjectNode quit(Integer id) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.removeUser(id, userBaseService.getLoginUserId());
        json.put(statusCode, status.getCode());
        return json;
    }

    /**
     * todo
     *
     * @param id
     * @return
     */
    @LoginRequired
    @GetMapping("/join")
    public ObjectNode join(Integer id) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.addUser(id, userBaseService.getLoginUserId(), false, 365);
        json.put(statusCode, status.getCode());
        return json;
    }

    /**
     * todo
     *
     * @param id
     * @return
     */
    @GetMapping("/member")
    public ObjectNode member(Integer id) {
        ObjectNode json = initJSON();
        json.set(jsonObs, userBaseService.findAllByGroup(id));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @param id
     * @return
     */
    @GetMapping("/one")
    public ObjectNode one(Integer id) {
        ObjectNode json = initJSON();
        json.set(jsonOb, getService().convertToJson(service.findById(id)));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @param page
     * @return
     */
    @LoginRequired
    @GetMapping("/my")
    public ObjectNode my(Integer page) {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        int size = 20;
        String direction = "desc";
        Page<UserGroup> userGroupPage;
        String attribute = "limit_number";
        userGroupPage = service.findAllByUser(user.getUserId(), BaseService.buildPageable(page, size, attribute, direction));
        List<UserGroup> tList = userGroupPage.getContent();
        ArrayNode jsons = getMapper().createArrayNode();
        if (tList.size() > 0) {
            for (UserGroup t : tList) {
                ObjectNode object = getMapper().convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
        }
        json.set("objects", jsons);
        json.put("total_pages", userGroupPage.getTotalPages());
        json.put("total_elements", userGroupPage.getTotalElements());
        json.put("page", page);
        json.put("size", size);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 创建群组
     *
     * @param name name
     * @return json
     */
    @LoginRequired
    @GetMapping("/create")
    public ObjectNode create(String name) {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        UserGroup group = new UserGroup();
        group.setName(name);
        group.setOwnerId(user.getUserId());
        ReturnStatus status = service.create(group);
        json.put(statusCode, status.getCode());
        return json;
    }

    /**
     * 显示用户组
     *
     * @param page page
     * @return json
     */
    @GetMapping("/list")
    public ObjectNode list(Integer page, String name) {
        int size = 10;
        String direction = "desc";
        ObjectNode json = initJSON();
        Page<UserGroup> userGroupPage;
        if (name == null) {
            String attribute = "limitNumber";
            userGroupPage = normalList(page, size, attribute, direction);
        } else {
            String attribute = "limit_number";
            userGroupPage = service.findAllByName(name, BaseService.buildPageable(page, size, attribute, direction));
        }
        List<UserGroup> tList = userGroupPage.getContent();
        ArrayNode jsons = getMapper().createArrayNode();
        if (tList.size() > 0) {
            for (UserGroup t : tList) {
                ObjectNode object = getMapper().convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
        }
        json.set("objects", jsons);
        json.put("total_pages", userGroupPage.getTotalPages());
        json.put("total_elements", userGroupPage.getTotalElements());
        json.put("page", page);
        json.put("size", size);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
