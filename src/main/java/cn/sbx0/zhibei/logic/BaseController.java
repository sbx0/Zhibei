package cn.sbx0.zhibei.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公用基础 控制层
 *
 * @param <T>  实体类类型
 * @param <ID> 一般为Integer
 */
@Controller
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

}
