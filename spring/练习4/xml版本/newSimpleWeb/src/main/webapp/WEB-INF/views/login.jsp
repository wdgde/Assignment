<%--
  Created by IntelliJ IDEA.
  User: CMSZ
  Date: 2019/9/16
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Simple page</title>
</head>
<body>
<form:form method="GET" action="login" modelAttribute="login-form">
    <form:input type="text" path="userId" id="user" />
    <form:input type="password" path="userPwd" id="password" />
    <input type="submit" value="go" />
</form:form>

</body>
</html>
