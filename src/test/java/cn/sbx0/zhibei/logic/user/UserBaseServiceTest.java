package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.base.UserBaseView;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
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
    public void userAgent() {
        UserAgentAnalyzer uaa = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withCache(10000)
                .build();

        UserAgent agent = uaa.parse("Mozilla/5.0 (Linux; Android 10; OS105 Build/QQ1B.200205.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/74.0.3729.186 Mobile Safari/537.36 Html5Plus/1.0 (Immersed/24.0)");

        for (String fieldName : agent.getAvailableFieldNamesSorted()) {
            System.out.println(fieldName + " = " + agent.getValue(fieldName));
        }
        // OperatingSystemClass client 是PC还是Mobile还是Tablet
        // DeviceName device Google Nexus 7
        // OperatingSystemNameVersionMajor system Windows 10
        // AgentNameVersionMajor agent Edge 80
    }

    @Test
    public void save() {
        UserBase user = new UserBase();
        user.setName("1");
        UserBase newUser = service.save(user);
        System.out.println(newUser);
    }

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