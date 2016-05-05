<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
		<title>Diagram Details</title>
	</head>
	<body>
		<div>
			<h1>Diagram Details</h1>
			<div>
				<table>
					<tr>
						<th>Image:</th>
						<td><img src="image?diagramId=<%=request.getAttribute("diagramId") %>"></td>
					</tr>
					<tr>
						<th>Title:</th>
						<td><%=request.getAttribute("title") %></td>
					</tr>
					<tr>
						<th>Description:</th>
						<td><%=request.getAttribute("description") %></td>
					</tr>
				</table>
			</div>
			<hr>
			<div style="text-align: center;">
				<h2>Comments</h2>
				<% for(Map<String,String> comments: (List<Map<String,String>>)request.getAttribute("comments")) { %>
					<div style="margin: 5px 50px;">
						<div><img src="<%=comments.get("imageUrl") %>"> <%=comments.get("userName") %></div>
						<div style="margin-left:50px; padding:5px;"><%=comments.get("comment") %></div>
					</div>
				<% } %>
					<form action="comment" method="post">
						<input type="hidden" name="diagramId" value="<%=request.getAttribute("diagramId") %>">
						<textarea name="comment" rows="5" cols="40"></textarea>
						<p><button type="submit">Submit</button></p>
					</form>
			</div>
			<div>
				<a id="mainMenu" href="<%=request.getContextPath()%>/cacoo/">Main Menu</a>
			</div>
		</div>
	</body>
</html>