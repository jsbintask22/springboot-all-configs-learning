package cn.jsbintask.springbootallconfigs.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 14:18
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<SecondFilter> secondFilterFilterRegistrationBean() {
        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecondFilter());
        registrationBean.setOrder(1001);

        registrationBean.addUrlPatterns("/**");

        return registrationBean;
    }
}
