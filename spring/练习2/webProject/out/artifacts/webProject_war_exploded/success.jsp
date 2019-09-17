<%@ page import="com.cmit.servlet.AccountBean" %><%--
  Created by IntelliJ IDEA.
  User: CMSZ
  Date: 2019/9/13
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<body>
<%
    AccountBean account = (AccountBean)session.getAttribute("account");
%>
username:<%= account.getUsername()%><br>
password:<%= account.getPassword() %></body>
</body>
</html>
