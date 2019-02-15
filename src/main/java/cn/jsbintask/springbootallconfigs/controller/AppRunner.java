package cn.jsbintask.springbootallconfigs.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 16:34
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("AppRunner.run");
    }
}
