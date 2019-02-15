package cn.jsbintask.springbootallconfigs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 12:00
 */
@Configuration
public class TomcatConfig {

    @Bean
    @Autowired
    public TomcatWebServerFactoryCustomizer factoryCustomizerAutoConfiguration(Environment environment, ServerProperties serverProperties) {
            serverProperties.setPort(8888);
        return new TomcatWebServerFactoryCustomizer(environment, serverProperties);
    }
}
