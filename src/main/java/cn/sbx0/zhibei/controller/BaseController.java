package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    // JSON返回操作状态一览 End
    ObjectMapper mapper;
    protected ObjectNode json;

    @Resource
    UserService userService;

    /**
     * 获取服务层 子类必须重写
     *
     * @return 对应的服务层
     */
    public abstract BaseService<T, ID> getService();

    /**
     * 分页查询列表
     * 返回结果是json
     *
     * @param page
     * @param size
     * @param session
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ObjectNode list(Integer page, Integer size, HttpSession session, HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(session, request);
        if (user != null) {
            if (userService.checkPermission(request, user)) {
                Page<T> tPage = getService().findAll(BaseService.buildPageable(page, size, BaseService.buildSort("id", "ASC")));
                List<T> tList = tPage.getContent();
                if (tList != null && tList.size() > 0) {
                    ArrayNode jsons = mapper.createArrayNode();
                    for (T t : tList) {
                        ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                        jsons.add(object);
                    }
//                    json.set(tList.get(0).getClass().getSimpleName().toLowerCase(), jsons);
                    json.set("objects", jsons);
                }
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
     * 根据ID查询实体
     *
     * @param id id
     * @return 实体
     */
    @ResponseBody
    @GetMapping("/{id}")
    public ObjectNode one(@PathVariable("id") ID id, HttpSession session, HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(session, request);
        if (user != null) {
            if (userService.checkPermission(request, user)) {
                T t = getService().findById(id);
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
//                json.set(t.getClass().getSimpleName().toLowerCase(), object);
                json.set("object", object);
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
     * 增加 / 修改 实体类
     *
     * @param t 范类
     * @return json
     */
    @ResponseBody
    @PostMapping("/add")
    public ObjectNode add(T t, HttpSession session, HttpServletRequest request) {
        json = mapper.createObjectNode();
        try {
            User user = userService.getUser(session, request);
            if (user != null) {
                if (userService.checkPermission(request, user)) {
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
    @ResponseBody
    @GetMapping("/delete")
    public ObjectNode delete(ID id, HttpSession session, HttpServletRequest request) {
        json = mapper.createObjectNode();
        try {
            User user = userService.getUser(session, request);
            if (user != null) {
                if (userService.checkPermission(request, user)) {
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
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

}
