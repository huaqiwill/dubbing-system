package com.moyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * @author 31734
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MoyinApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MoyinApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
