package cn.jsbintask.springbootallconfigs.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 14:11
 */
@Component
@Order(1000)
public class FirstFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //to do something.
        System.out.println("FirstFilter.doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
