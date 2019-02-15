package cn.jsbintask.springbootallconfigs.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 15:20
 */
@RestController
@RequestMapping
public class PropertiesController {
    @Autowired
    private Environment environment;

    @GetMapping("/path")
    public String getPath() {
        return environment.getProperty("server.port");
    }
}
