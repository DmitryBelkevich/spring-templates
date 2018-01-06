<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Registration</h1>

<form action="/security/registration" method="POST">
    <input name="username" type="text">
    <input name="password" type="password">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <button type="submit">Submit</button>
</form>

</body>
</html>
