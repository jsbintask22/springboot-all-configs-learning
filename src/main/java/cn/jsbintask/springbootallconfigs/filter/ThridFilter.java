package cn.jsbintask.springbootallconfigs.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 14:28
 */
@WebFilter(filterName = "ThridFilter", urlPatterns = "/**")
@Order(1002)
public class ThridFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("ThridFilter.doFilter");
        chain.doFilter(req, resp);
    }

}
