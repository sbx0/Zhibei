package cn.sbx0.zhibei.logic.technical.achievements;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechnicalAchievementsServiceTest {
    @Resource
    TechnicalAchievementsService service;

    @Test
    public void init() {
        service.init();
    }
}