package cn.sbx0.zhibei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class ZhibeiApplication {

    @PostConstruct
    void started() {
        // 设置时区为东八区
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ZhibeiApplication.class, args);
    }

}

