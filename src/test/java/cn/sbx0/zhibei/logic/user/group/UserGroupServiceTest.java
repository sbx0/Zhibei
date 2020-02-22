package cn.sbx0.zhibei.logic.user.group;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserGroupServiceTest {
    @Resource
    private UserGroupService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName("智贝");
        userGroup.setOwnerId(1);
        service.create(userGroup);
    }

    @Test
    public void addUser() {
        service.addUser(1, 2, false, 7);
    }

    @Test
    public void removeUser() {
        service.removeUser(1, 2);
    }
}