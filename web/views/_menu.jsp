<%--
  Created by IntelliJ IDEA.
  User: memphis8
  Date: 05.07.19
  Time: 12:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>menu</title>
</head>
<body>

<div style="padding: 5px;">

    <a href="${pageContext.request.contextPath}/">Home</a>
    |
    <a href="${pageContext.request.contextPath}/productList">Product List</a>
    |
    <a href="${pageContext.request.contextPath}/userInfo">My Account Info</a>
    |
    <a href="${pageContext.request.contextPath}/login">Login</a>

</div>
</body>
</html>
