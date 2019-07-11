package com.memphis.simplewebapp.servlet;

import com.memphis.simplewebapp.beans.UserAccount;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserInfoServlet",urlPatterns = {"/userInfo"})
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        UserAccount userAccount = (UserAccount) session.getAttribute("loginedUser");

        if(userAccount==null){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("user",userAccount);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/userInfoView.jsp");
        dispatcher.forward(request,response);

    }
}
