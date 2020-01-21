package cn.sbx0.zhibei.logic.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaseServiceTest {
    @Resource
    UserBaseService userBaseService;

    @Test
    public void register() {
        UserBaseView userBaseView = new UserBaseView();
        userBaseView.setName("test");
        userBaseView.setEmail("test@sbx0.cn");
        userBaseView.setPassword("test");
        int status = userBaseService.register(userBaseView);
        System.out.println(status);
    }
}