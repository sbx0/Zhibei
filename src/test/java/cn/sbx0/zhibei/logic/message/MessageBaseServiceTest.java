package cn.sbx0.zhibei.logic.message;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MessageBaseServiceTest {
    @Resource
    MessageBaseService service;

    @Test
    void send() {
        MessageBase message = new MessageBase();
        message.setType("msg");
        message.setSendUserId(1);
        message.setSendTime(new Date());
        message.setReceiveUserId(7);
        message.setContent("fuck");
        service.save(message);
    }

    @Test
    void notice() {
        System.out.println(service.notice(7));
    }

    @Test
    void read() {
        System.out.println(service.read(1, 7));
    }

    @Test
    void receive() {
        List<MessageBase> messageBases = service.receive(7, 1, 10);
    }
}