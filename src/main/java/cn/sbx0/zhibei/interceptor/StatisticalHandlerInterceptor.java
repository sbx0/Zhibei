package cn.sbx0.zhibei.interceptor;

import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import cn.sbx0.zhibei.logic.statistical.service.StatisticalUserService;
import cn.sbx0.zhibei.tool.RequestTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 统计拦截器
 */
@Component
public class StatisticalHandlerInterceptor implements HandlerInterceptor {
    @Resource
    private StatisticalUserService service;

    /**
     * 统计后放行
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return boolean
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("preHandle begin");
        logger.info(RequestTools.getUserAgent(request));
        StatisticalUser user = new StatisticalUser();
        user.setIp(RequestTools.getIpAddress(request));
        user.setClient(RequestTools.getClient(request));
        logger.info(user.getIp());
        user.setTime(new Date());
        service.save(user);
        logger.info("preHandle end");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
