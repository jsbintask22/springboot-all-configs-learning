package cn.jsbintask.springbootallconfigs.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 14:16
 */
public class SecondFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //to do something.

        System.out.println("SecondFilter.doFilter");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
