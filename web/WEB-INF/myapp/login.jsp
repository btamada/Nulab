<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="">
    <title>Login</title>
</head>
<body>
    <h1>You have successfully logged in!</h1>
    <form name="myForm" method="post" action="cacooapi">
        <fieldset>
            <label for="api">Select an API to call:</label>
            <select name="api" id="api" onchange="this.form.submit()">
                <option selected="selected"></option>
                <option>Account</option>
                <option>Folders</option>
                <option>Diagrams</option>
                <option>License</option>
            </select>
        </fieldset>
    </form>
    <a href="/">Back to Home Page</a>
</body>
</html>
