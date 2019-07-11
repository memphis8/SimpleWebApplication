package com.memphis.simplewebapp.servlet;

import com.memphis.simplewebapp.beans.Product;
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
import java.util.List;

@WebServlet(name = "ProductListServlet",urlPatterns = {"/productList"})
public class ProductListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Connection connection = MyUtils.getStoredConnection(request);

        String errorString = null;
        List<Product> products = null;

        try {
            products = DBUtils.queryProduct(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("errorString",errorString);
        request.setAttribute("productList",products);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/productListView.jsp");
        dispatcher.forward(request, response);
    }
}
