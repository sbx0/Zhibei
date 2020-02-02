package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.base.UserBaseView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaseServiceTest {
    @Resource
    UserBaseService service;
    @Resource
    HttpSession session;
    @Resource
    HttpServletResponse response;

    @Test
    public void main() {
        register();
        login();
    }

    @Test
    public void login() {
        UserBaseView userBaseView = new UserBaseView();
        userBaseView.setEmail("test@sbx0.cn");
        userBaseView.setPassword("test");
        System.out.println(service.login(userBaseView, session, response));
    }

    @Test
    public void register() {
        UserBaseView userBaseView = new UserBaseView();
        userBaseView.setName("test");
        userBaseView.setEmail("test@sbx0.cn");
        userBaseView.setPassword("test");
        System.out.println(service.register(userBaseView));
    }

    @Test
    @Transactional
    public void heartbeat() {
        int id = 1;
        Date date = new Date();
        service.heartbeat(id, date);
    }
}