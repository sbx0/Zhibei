package cn.sbx0.zhibei.annotation;

import cn.sbx0.zhibei.entity.Log;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.LogService;
import cn.sbx0.zhibei.service.UserService;
import cn.sbx0.zhibei.tool.RequestTools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志注解切面类 实现注解
 */
@Aspect
@Component
public class LogAspect {
    @Resource
    LogService logService;
    @Resource
    UserService userService;
    private Long runTime = 0L;

    // 切入点
    @Pointcut("@annotation(cn.sbx0.zhibei.annotation.LogRecord)")
    private void pointcut() {

    }

    // 后置切面
    @After(value = "pointcut()")
    public void After(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName(); // 类名
        // 获取此次请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String methodName = joinPoint.getSignature().getName(); // 方法名
        User user = userService.getUser(request);
        if (user != null) {
            if (user.getRole().getName().equals("admin")) {
                return;
            }
        }
        Log log = new Log();
        log.setUser(user);
        log.setMethod(methodName);
        log.setIp(RequestTools.getIpAddress(request));
        log.setClassName(className);
        if (request.getQueryString() != null) {
            log.setArgs(request.getQueryString());
            log.setUrl(request.getRequestURL().toString() + "?" + request.getQueryString());
        } else {
            log.setUrl(request.getRequestURL().toString());
        }
        log.setTime(new Date());
        log.setRunTime(runTime);
        logService.save(log);
    }

    // 环绕通知
    @Around(value = "pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            runTime = end - start;
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            runTime = end - start;
            throw e;
        }
    }

}
