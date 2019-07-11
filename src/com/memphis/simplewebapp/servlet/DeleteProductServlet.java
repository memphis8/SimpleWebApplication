package com.memphis.simplewebapp.servlet;

import com.memphis.simplewebapp.utils.DBUtils;
import com.memphis.simplewebapp.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "DeleteProductServlet",urlPatterns = {"/deleteProduct"})
public class DeleteProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = MyUtils.getStoredConnection(request);
        String errorString = null;

        String code = request.getParameter("code");

        try {
            DBUtils.deleteProduct(connection,code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        if(errorString!=null){
            request.setAttribute("errorString",errorString);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/deleteProductErrorView.jsp");
            dispatcher.forward(request,response);
        }
        else {
            response.sendRedirect(request.getContextPath()+"/productList");
        }
    }
}
