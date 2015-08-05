<%-- 
    Document   : error
    Created on : Jul 30, 2015, 9:36:26 AM
    Author     : izielinski
--%>

<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ERROR</h1>
        <%= exception.getMessage() %>
    </body>
</html>
