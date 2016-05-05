<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
    <link rel="stylesheet" href="">
    <title>My Cacoo API Webapp</title>
</head>
<body>
<div>
    <h1>Welcome to My Cacoo API Webapp!</h1>
    <div>
        <form id="myForm" method="post" action="myapp/login">
            <table id="myTable">
                <tr>
                    <td>Login ID:</td>
                    <td><input type="text" name="login"></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><button type="submit">Login</button></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>