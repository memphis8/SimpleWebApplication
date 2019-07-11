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

@WebServlet(name = "CreateProductServlet",urlPatterns = {"/createProduct"})
public class CreateProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection connection = MyUtils.getStoredConnection(request);

        String errorString = null;

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        String codeRegex =  "\\w+";

        if((name.equals("")||priceStr.equals(""))){
            errorString = "Fill all fields!";
        }
        else if(code==null||!code.matches(codeRegex)){
            errorString = "Product code invalid!";
        }


        if(errorString==null){

            float price = Float.parseFloat(priceStr);

            Product product = new Product(code,name,price);

            try {
                DBUtils.insertProduct(connection,product);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }

            request.setAttribute("product", product);
        }

        request.setAttribute("errorString", errorString);

        if(errorString!=null){
            doGet(request,response);
        }
        else {

            response.sendRedirect(request.getContextPath()+"/productList");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/createProductView.jsp");
        dispatcher.forward(request,response);
    }
}
