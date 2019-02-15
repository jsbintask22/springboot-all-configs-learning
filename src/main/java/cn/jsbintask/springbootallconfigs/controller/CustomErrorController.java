package cn.jsbintask.springbootallconfigs.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 15:57
 */
@RestController
@RequestMapping
public class CustomErrorController implements ErrorController {
    public static final String ERROR_PATH = "/error";

    @RequestMapping(path = ERROR_PATH)
    public String error() {
        return "custom error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
