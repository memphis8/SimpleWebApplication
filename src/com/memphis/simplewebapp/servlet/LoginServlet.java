package com.memphis.simplewebapp.servlet;

import com.memphis.simplewebapp.beans.UserAccount;
import com.memphis.simplewebapp.utils.DBUtils;
import com.memphis.simplewebapp.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        boolean isRemembered = "Y".equals(request.getParameter("rememberMe"));


        UserAccount user = null;

        boolean hasError = false;
        String errorString = null;

        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                // Найти user в DB.
                user = DBUtils.findUser(conn, username, password);

                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }


        if (hasError) {
            user = new UserAccount();
            user.setUserName(username);
            user.setPassword(password);

            // Сохранить информацию в request attribute перед forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);


            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/views/loginView.jsp");
            dispatcher.forward(request, response);
        }


        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);


            if (isRemembered) {
                MyUtils.storeUserCookie(response, user);
            }

            else {
                MyUtils.deleteUserCookie(response);
            }


            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/loginView.jsp");
        dispatcher.forward(request, response);
    }
}
