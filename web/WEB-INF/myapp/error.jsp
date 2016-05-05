<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/style.css">
    <title>Error</title>
</head>
<body>
    <h1>Uh oh... We have encountered a problem.</h1>
    <p><%=request.getAttribute("message")%></p>
    <a href="/">Back to Home Page</a>
</body>
</html>
