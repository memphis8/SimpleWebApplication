package com.memphis.simplewebapp.filters;


import com.memphis.simplewebapp.dbconn.ConnectionUtils;
import com.memphis.simplewebapp.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;


@WebFilter(filterName = "jdbcFilter",urlPatterns = {"/*"})
public class JDBCFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void destroy() {

    }

    // Проверить является ли Servlet цель текущего request?
    private boolean needJDBC(HttpServletRequest request) {
        System.out.println("JDBC Filter");

        String servletPath = request.getServletPath();

        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            // => /spath/*
            urlPattern = servletPath + "/*";
        }


        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();


        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // Открыть  connection (соединение) только для request со специальной ссылкой.
        // (Например ссылка к servlet, jsp, ..)
        // Избегать открытия Connection для обычных запросов.
        // (Например image, css, javascript,... )
        if (this.needJDBC(req)) {

            System.out.println("Open Connection for: " + req.getServletPath());

            Connection conn = null;
            try {
                // Создать объект Connection подключенный к database.
                conn = ConnectionUtils.getConnection();
                // Настроить автоматический commit false, чтобы активно контролировать.
                conn.setAutoCommit(false);

                // Сохранить объект Connection в attribute в request.
                MyUtils.storeConnInAttribute(request, conn);

                // Разрешить request продвигаться далее.
                // (Далее к следующему Filter tiếp или к цели).
                chain.doFilter(request, response);

                // Вызвать метод commit() чтобы завершить транзакцию с DB.
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    ConnectionUtils.rollbackQuietly(conn);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw new ServletException();
            } finally {
                try {
                    ConnectionUtils.closeQuietly(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Для обычных request (image,css,html,..)
        // не нужно открывать connection.
        else {
            // Разрешить request продвигаться далее.
            // (Далее к следующему Filter tiếp или к цели).
            chain.doFilter(request, response);
        }

    }
}
