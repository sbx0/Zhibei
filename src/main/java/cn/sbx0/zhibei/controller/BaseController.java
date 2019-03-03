package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UserService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 公用基础 控制层
 *
 * @param <T>  实体类类型
 * @param <ID> 一般为Integer
 */
@Controller
public abstract class BaseController<T, ID> {
    static final String STATUS_NAME = "status"; // JSON返回状态字段名
    // JSON返回操作状态一览 Begin
    static final int STATUS_CODE_SUCCESS = 0; // 成功
    static final int STATUS_CODE_FILED = 1; // 失败
    static final int STATUS_CODE_EXCEPTION = 2; // 异常
    static final int STATUS_CODE_NOT_LOGIN = 3; // 未登录
    static final int STATUS_CODE_TIMES_LIMIT = 4; // 限制
    static final int STATUS_CODE_NOT_FOUND = 5; // 不存在
    static final int STATUS_CODE_NO_PERMISSION = 6; // 无权限
    static final int STATUS_CODE_REPEAT = 7; // 重复操作
    static final int STATUS_CODE_TIME_OUT = 8; // 超时
    static final int STATUS_CODE_PARAMETER_ERROR = 9; // 参数错误
    // JSON返回操作状态一览 End
    ObjectMapper mapper;
    protected ObjectNode json;

    @Resource
    protected UserService userService;

    /**
     * 获取服务层 子类必须重写
     *
     * @return 对应的服务层
     */
    public abstract BaseService<T, ID> getService();

    /**
     * 获取对象所有的属性名，属性类型
     *
     * @return 实体
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/attribute")
    public ObjectNode getAttribute() {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (userService.checkPermission(user)) {
                List<Map> attributes = getService().getAttribute();
                ArrayNode jsons = mapper.createArrayNode();
                for (Map info : attributes) {
                    ObjectNode object = mapper.convertValue(info, ObjectNode.class);
                    jsons.add(object);
                }
                json.set("attributes", jsons);
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 需要权限的列表
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/list")
    public ObjectNode adminList(Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.All.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (userService.checkPermission(user)) {
                json = list(page, size, attribute, direction);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 无需权限版列表
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/normal/list")
    public ObjectNode normalList(Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        json = list(page, size, attribute, direction);
        return json;
    }

    /**
     * 分页查询列表
     * 返回结果是json
     *
     * @param page
     * @param size
     * @return
     */
    public ObjectNode list(Integer page, Integer size, String attribute, String direction) {
        json = mapper.createObjectNode();
        Page<T> tPage = getService().findAll(BaseService.buildPageable(page, size, attribute, direction));
        List<T> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (T t : tList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
            json.put("total_pages", tPage.getTotalPages());
            json.put("total_elements", tPage.getTotalElements());
            json.put("page", page);
            json.put("size", size);
        }
        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        return json;
    }

    /**
     * 无需权限版根据id查询实体 去除了一些敏感信息
     *
     * @param id
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/normal")
    public ObjectNode normalOne(ID id) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = one(id);
        return json;
    }

    /**
     * 需要权限版 根据id查询实体
     *
     * @param id
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/{id}")
    public ObjectNode adminOne(@PathVariable("id") ID id) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.All.class));
        User user = userService.getUser();
        if (user != null) {
            if (userService.checkPermission(user)) {
                json = one(id);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 根据ID查询实体
     *
     * @param id id
     * @return 实体
     */
    public ObjectNode one(ID id) {
        json = mapper.createObjectNode();
        T t = getService().findById(id);
        if (t == null) t = getService().getEntity();
        ObjectNode object = mapper.convertValue(t, ObjectNode.class);
        json.set("object", object);
        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        return json;
    }

    /**
     * 增加 / 修改 实体类
     *
     * @param t 范类
     * @return json
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/add")
    public ObjectNode add(T t) {
        json = mapper.createObjectNode();
        try {
            User user = userService.getUser();
            if (user != null) {
                if (userService.checkPermission(user)) {
                    if (getService().save(t)) {
                        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_FILED);
                    }
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
                }
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            }
        } catch (Exception e) {
            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
            json.put("msg", e.getMessage());
        }
        return json;
    }

    /**
     * 根据id删除实体类
     *
     * @param id id
     * @return json
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/delete")
    public ObjectNode delete(ID id) {
        json = mapper.createObjectNode();
        try {
            User user = userService.getUser();
            if (user != null) {
                if (userService.checkPermission(user)) {
                    if (getService().deleteById(id)) {
                        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_FILED);
                    }
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
                }
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            }
        } catch (Exception e) {
            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
            json.put("msg", e.getMessage());
        }
        return json;
    }

    /**
     * 格式化日期
     * 用于解决页面上输入时间控件无法使用的问题
     *
     * @param binder binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // CustomDateEditor为自定义日期编辑器
    }

}
