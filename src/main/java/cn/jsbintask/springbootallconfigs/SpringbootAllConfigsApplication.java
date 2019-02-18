package cn.jsbintask.springbootallconfigs;

import cn.jsbintask.springbootallconfigs.controller.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.WebApplicationInitializer;

/**
 * @author jsbintask@gmail.com
 * @date
 */
@SpringBootApplication
@ServletComponentScan
public class SpringbootAllConfigsApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootAllConfigsApplication.class, args);

        /* way one to init */
        HelloController bean = applicationContext.getBean(HelloController.class);
        bean.init();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootApplication.class);
    }
}

