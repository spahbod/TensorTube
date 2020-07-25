package pl.tube.tensortube.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.tube.tensortube.controller.UserManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);


    public static final String LOGIN_PAGE = "/login.xhtml";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // managed bean name is exactly the session attribute name
        UserManager userManager = (UserManager) httpServletRequest.getSession().getAttribute("userManager");

        if (userManager != null) {
            if (userManager.isLoggedIn()) {
                // user is logged in, continue request
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // user is not logged in, redirect to login page
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
            }
        } else {
            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.debug("loginFilter initialized");
    }

    @Override
    public void destroy() {
        // close resources
    }
}
