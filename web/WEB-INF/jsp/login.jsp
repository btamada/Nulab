<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
		<title>Login</title>
	</head>
	<body>
		<div>
			<form action="j_security_check" method="post">
				<table style="width:300px; margin:100px auto;">
					<tr>
						<td>Login ID:</td><td><input type="text" name="j_username"></td>
					</tr>
					<tr>
						<td>Password:</td><td><input type="password" name="j_password"></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center;"><button type="submit">Login</button></td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
