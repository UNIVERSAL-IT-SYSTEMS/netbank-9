<%--
  Created by IntelliJ IDEA.
  User: Szabolcs
  Date: 2016. 03. 22.
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>Custom Login Page</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link href="css/signin.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <%

        if (request.getParameter("error") != null) {
    %>
    <div class="alert alert-danger" data-dismiss="alert" role="alert">
        <button type="button" class="close" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        <strong>Invalid Credentials</strong>
    </div>
    <%
        }
    %>
    <form name='f' action="login" method='POST' class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputUserName" class="sr-only">Email address</label>
        <input type='text' name='username' id='inputUserName' value='' class="form-control" placeholder="Username"
               required>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type='password' id="inputPassword" name='password' class="form-control" placeholder="Password" required/>
        <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="submit"/>
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>