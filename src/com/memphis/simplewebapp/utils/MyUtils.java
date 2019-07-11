package com.memphis.simplewebapp.utils;

import com.memphis.simplewebapp.beans.UserAccount;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class MyUtils {

    private static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    public static void storeConnInAttribute(ServletRequest request, Connection connection) {
        request.setAttribute(ATT_NAME_CONNECTION, connection);
    }

    public static Connection getStoredConnection(ServletRequest request) {
        return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
    }

    public static void storeLoginedUser(HttpSession httpSession, UserAccount loginedUser) {
        httpSession.setAttribute("loginedUser", loginedUser);
    }

    public static UserAccount getLoginedUser(HttpSession httpSession) {
        return (UserAccount) httpSession.getAttribute("loginedUser");
    }

    public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
        int cookieLIfeTime = 24 * 60 * 60;
        cookieUserName.setMaxAge(cookieLIfeTime);
        response.addCookie(cookieUserName);
    }

    public static String getUserNameByCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }

}

