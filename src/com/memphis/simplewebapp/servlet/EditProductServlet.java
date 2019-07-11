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

@WebServlet(name = "EditProductServlet",urlPatterns = {"/editProduct"})
public class EditProductServlet extends HttpServlet {
    // Отобразить страницу редактирования продукта.

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);

        String code = (String) request.getParameter("code");

        Product product = null;

        String errorString = null;

        try {
            product = DBUtils.findProduct(conn, code);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        // Ошибки не имеются.
        // Продукт не существует для редактирования (edit).
        // Redirect sang trang danh sách sản phẩm.
        if (errorString != null && product == null) {
            response.sendRedirect(request.getServletPath() + "/productList");
            return;
        }

        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", product);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/editProductView.jsp");
        dispatcher.forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
        String errorString = null;

        String code = (String) request.getParameter("code");
        String name = (String) request.getParameter("name");
        String priceStr = (String) request.getParameter("price");

        String codeRegex =  "\\w+";

        if((name.equals("")||priceStr.equals(""))){
            errorString = "Fill all fields!";
        }
        else if(code==null||!code.matches(codeRegex)){
            errorString = "Product code invalid!";
        }



        try {
            float price = Float.parseFloat(priceStr);
            Product product = new Product(code, name, price);
            DBUtils.updateProduct(conn, product);
            request.setAttribute("product", product);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }

        request.setAttribute("errorString", errorString);


        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/views/editProductView.jsp");
            dispatcher.forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }
    }
}
