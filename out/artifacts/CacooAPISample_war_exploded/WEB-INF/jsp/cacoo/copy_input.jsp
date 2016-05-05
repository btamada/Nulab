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
			<div>
				<form action="copyInput" method="post">
					<input type="hidden" name="diagramId" value="<%=request.getAttribute("diagramId")%>">
					<table>
						<tr>
							<td>Title</td><td><input type="text" name="title" value="diagram title"></td>
						</tr>
						<tr>
							<td>Description</td><td><input type="text" name="description"></td>
						</tr>
					</table>
					<button id="submitBtn" type="submit">Submit</button>
				</form>
			</div>			
			<div>
				<a id="mainMenu" href="<%=request.getContextPath()%>/cacoo/">Main Menu</a>
			</div>
		</div>
	</body>
</html>