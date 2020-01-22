package cn.sbx0.zhibei.logic.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaseServiceTest {
    @Resource
    UserBaseService userBaseService;
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
        System.out.println(userBaseService.login(userBaseView, session, response));
    }

    @Test
    public void register() {
        UserBaseView userBaseView = new UserBaseView();
        userBaseView.setName("test");
        userBaseView.setEmail("test@sbx0.cn");
        userBaseView.setPassword("test");
        System.out.println(userBaseService.register(userBaseView));
    }
}