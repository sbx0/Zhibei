package cn.sbx0.zhibei.interceptor;

import cn.sbx0.zhibei.logic.statistical.service.StatisticalUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        service.handlerInterceptor(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
