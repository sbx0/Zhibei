package cn.sbx0.zhibei.annotation;

import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * 经验注解切面类 实现注解
 */
@Aspect
@Component
public class ExpAspect {
    @Resource
    UserService userService;

    // 切入点
    @Pointcut("@annotation(cn.sbx0.zhibei.annotation.ExpFunction)")
    private void pointcut() {

    }

    // 后置切面
    @After(value = "pointcut()")
    public void After(JoinPoint joinPoint) {
        User user = userService.getUser();
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        ExpFunction annotation = method.getAnnotation(ExpFunction.class);
        double getExp = Double.parseDouble(annotation.value());
        double exp = user.getExp();
        double exp_max = user.getExp_max();
        int level = user.getLevel();
        double sum = exp + getExp;
        if (sum <= exp_max) {
            user.setExp((int) (sum * 10) / 10.0);
        } else {
            level++;
            exp = sum - exp_max;
            exp_max = exp_max * level * 5;
            user.setExp((int) (exp * 10) / 10.0);
            user.setExp_max((int) (exp_max * 10) / 10.0);
        }
        user.setLevel(level);
        userService.save(user);
    }

    // 环绕通知
    @Around(value = "pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
