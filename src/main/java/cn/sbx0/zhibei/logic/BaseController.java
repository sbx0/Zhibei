package cn.sbx0.zhibei.logic;

import cn.sbx0.zhibei.annotation.RoleCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public abstract class BaseController<T, ID> {
    protected final String statusCode = "status";
    protected final String statusMsg = "msg";
    protected final String jsonOb = "object";
    protected final String jsonObs = "objects";

    /**
     * 获取服务层 子类必须重写
     *
     * @return 对应的服务层
     */
    public abstract BaseService<T, ID> getService();

    protected ObjectNode initJSON() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createObjectNode();
    }

    protected ArrayNode initJSONs() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createArrayNode();
    }

    protected ObjectMapper getMapper() {
        return new ObjectMapper();
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


    /**
     * 通用保存
     *
     * @param t t
     * @return json
     */
    @RoleCheck(values = {"admin", "webSiteOwner"})
    @PostMapping("/admin/save")
    public ObjectNode adminSave(T t) {
        ObjectNode json = initJSON();
        try {
            T r = getService().save(t);
            if (r == null) json.put(statusCode, ReturnStatus.failed.getCode());
            else json.put(statusCode, ReturnStatus.success.getCode());
        } catch (Exception e) {
            json.put(statusCode, ReturnStatus.failed.getCode());
            json.put(statusMsg, e.getMessage());
        }
        return json;
    }

    /**
     * 通用删除
     *
     * @param id id
     * @return json
     */
    @RoleCheck(values = {"admin", "webSiteOwner"})
    @GetMapping("/admin/delete")
    public ObjectNode adminDelete(ID id) {
        ObjectNode json = initJSON();
        if (getService().existsById(id)) {
            T t = getService().findById(id);
            if (getService().delete(t)) {
                json.put(statusCode, ReturnStatus.success.getCode());
            } else {
                json.put(statusCode, ReturnStatus.failed.getCode());
            }
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }


    /**
     * 通用查询一个
     *
     * @param id id
     * @return json
     */
    @RoleCheck(values = {"admin", "webSiteOwner"})
    @GetMapping("/admin/one")
    public ObjectNode adminFindById(ID id) {
        ObjectNode json = initJSON();
        boolean exist = getService().existsById(id);
        if (!exist) {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
            return json;
        }
        T t = getService().findById(id);
        if (t == null) {
            json.put(statusCode, ReturnStatus.emptyResult.getCode());
        } else {
            ObjectNode objectNode = getMapper().convertValue(t, ObjectNode.class);
            json.set("object", objectNode);
            json.put(statusCode, ReturnStatus.success.getCode());
        }
        return json;
    }

    /**
     * 获取对象所有的属性名，属性类型
     *
     * @return json
     */
    @RoleCheck(values = {"admin", "webSiteOwner"})
    @GetMapping("/admin/attribute")
    public ObjectNode adminGetAttribute() {
        ObjectNode json = initJSON();
        List<Map> attributes = getService().getAttribute();
        ArrayNode jsons = getMapper().createArrayNode();
        for (Map info : attributes) {
            ObjectNode object = getMapper().convertValue(info, ObjectNode.class);
            jsons.add(object);
        }
        json.set("objects", jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 分页查询列表
     *
     * @param page      page
     * @param size      size
     * @param attribute attribute
     * @param direction direction
     * @return json
     */
    @RoleCheck(values = {"admin", "webSiteOwner"})
    @GetMapping("/admin/list")
    public ObjectNode adminList(Integer page, Integer size, String attribute, String direction) {
        ObjectNode json = initJSON();
        Page<T> tPage = getService().findAll(BaseService.buildPageable(page, size, attribute, direction));
        List<T> tList = tPage.getContent();
        ArrayNode jsons = getMapper().createArrayNode();
        if (tList.size() > 0) {
            for (T t : tList) {
                ObjectNode object = getMapper().convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
        }
        json.set("objects", jsons);
        json.put("total_pages", tPage.getTotalPages());
        json.put("total_elements", tPage.getTotalElements());
        json.put("page", page);
        json.put("size", size);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

}
