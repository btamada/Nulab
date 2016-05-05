<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
		<title>Copy Diagram</title>
	</head>
	<body>
		<div>
			<h1>Copy Diagram</h1>
			<p style="text-align:center"><%=request.getAttribute("message") %></p>
			<a style="display:inherit" href="<%=request.getContextPath()%>/cacoo/">Main Menu</a>
		</div>
	</body>
</html>