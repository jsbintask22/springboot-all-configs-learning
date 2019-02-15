package cn.jsbintask.springbootallconfigs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 16:43
 */
@Controller
@RequestMapping("/templates")
public class TemplateController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
