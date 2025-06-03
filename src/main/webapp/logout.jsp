<%-- 
    Document   : logout
    Created on : Jun 1, 2025, 8:09:25 PM
    Author     : mohamed gherab
--%>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
