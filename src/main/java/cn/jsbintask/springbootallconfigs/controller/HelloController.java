package cn.jsbintask.springbootallconfigs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 15:50
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    public void init() {
        System.out.println("HelloController.init");
    }
}
