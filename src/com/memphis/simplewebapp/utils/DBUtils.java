package com.memphis.simplewebapp.utils;

import com.memphis.simplewebapp.beans.Product;
import com.memphis.simplewebapp.beans.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static UserAccount findUser(Connection connection, String userName, String pass) throws SQLException {
        String sqlQuery = "Select USER_NAME , PASSWORD , GENDER from USER_ACCOUNT where USER_NAME = ? and PASSWORD = ?";
        PreparedStatement pstm = connection.prepareStatement(sqlQuery);
        pstm.setString(1, userName);
        pstm.setString(2, pass);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String gender = resultSet.getString("GENDER");
            return new UserAccount(userName, gender, pass);
        }
        return null;
    }

    public static UserAccount findUser(Connection connection, String userName) throws SQLException {
        String sqlQuery = "Select USER_NAME , PASSWORD , GENDER from USER_ACCOUNT where USER_NAME = ?";
        PreparedStatement pstm = connection.prepareStatement(sqlQuery);
        pstm.setString(1, userName);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String gender = resultSet.getString("GENDER");
            String pass = resultSet.getString("PASSWORD");
            return new UserAccount(userName, gender, pass);
        }
        return null;
    }

    public static List<Product> queryProduct(Connection conn) throws SQLException {
        String sql = "Select CODE, NAME, PRICE from PRODUCT";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Product> list = new ArrayList<Product>();

        while (rs.next()) {
            String code = rs.getString("CODE");
            String name = rs.getString("NAME");
            float price = rs.getFloat("PRICE");
            Product product = new Product(code, name, price);
            list.add(product);
        }
        return list;
    }

    public static Product findProduct(Connection conn, String code) throws SQLException {
        String sql = "Select CODE, NAME, PRICE  from PRODUCT a where CODE=?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            String name = rs.getString("Name");
            float price = rs.getFloat("Price");
            return new Product(code, name, price);
        }
        return null;
    }

    public static void updateProduct(Connection conn, Product product) throws SQLException {
        String sql = "Update PRODUCT set NAME =?, PRICE=? where CODE=? ";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, product.getName());
        pstm.setFloat(2, product.getPrice());
        pstm.setString(3, product.getCode());
        pstm.executeUpdate();
    }

    public static void insertProduct(Connection conn, Product product) throws SQLException {
        String sql = "Insert into PRODUCT(CODE, NAME,PRICE) values (?,?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, product.getCode());
        pstm.setString(2, product.getName());
        pstm.setFloat(3, product.getPrice());

        pstm.executeUpdate();
    }

    public static void deleteProduct(Connection conn, String code) throws SQLException {
        String sql = "Delete From PRODUCT where CODE= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, code);

        pstm.executeUpdate();
    }


}

