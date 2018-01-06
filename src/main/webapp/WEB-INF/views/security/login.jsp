<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<p><font color="green">You have been logged out</font></p>
<h1>Login with Username and Password</h1>
<form action="/security/login" method="POST">
    <table>
        <tbody>
        <tr>
            <td>User:</td>
            <td><input type="text" name="username" value=""></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td><input type="checkbox" name="_spring_security_remember_me" checked="checked" /></td>
        </tr>
        <tr>
            <td colspan="2"><input name="submit" type="submit" value="Login"></td>
        </tr>
        <tr>
            <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></td>
        </tr>
        </tbody>
    </table>
</form>

<a href="/security/registration">registration</a>

</body>
</html>
