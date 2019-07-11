package com.memphis.simplewebapp.filters;

import com.memphis.simplewebapp.beans.UserAccount;
import com.memphis.simplewebapp.utils.DBUtils;
import com.memphis.simplewebapp.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        UserAccount userAccount = MyUtils.getLoginedUser(session);
        if (userAccount != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        Connection connection = MyUtils.getStoredConnection(req);
        String cookieIsChecked = (String) session.getAttribute("COOKIE_CHECKED");
        if (cookieIsChecked == null && connection != null) {
            String userName = MyUtils.getUserNameByCookie(req);
            try {
                UserAccount user = DBUtils.findUser(connection, userName);
                MyUtils.storeLoginedUser(session, user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }

        chain.doFilter(request, response);


    }

}
