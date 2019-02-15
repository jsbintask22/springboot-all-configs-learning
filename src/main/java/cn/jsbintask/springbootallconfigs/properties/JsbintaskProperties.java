package cn.jsbintask.springbootallconfigs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 15:24
 */
@ConfigurationProperties(prefix = "cn.jsbintask")
@EnableConfigurationProperties
@Component
@Data
public class JsbintaskProperties {
    private String name;
    private int age;
}
