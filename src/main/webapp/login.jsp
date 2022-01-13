<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
</head>
<body>
<h1>Sign In</h1>
<form action="LoginServlet" method="post">
    <input type="text" name="username" required/>
    <br>
    <input type="password" name="password" required/>
    <br>
    <input type="submit" value="Login" name="login"/>
    <input type="submit" value="Sign Up"/>
    <p>${errorMessage}</p>
</form>
</body>
</html>
