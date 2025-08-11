<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板</title>
<style>
    body{
        background-color: #e9ecef;
        margin: 0;
        padding: 20px;
    }
    h1 {
        text-align: center;
        color: #333;
    }
    .login-container {
        background-color: #fff;
        padding: 20px;
        max-width: 400px;
        margin: 40px auto;
        border-radius: 8px;
        box-shadow: 0 0 15px rgba(0,0,0,0.2);
    }
    form input[type=text], form input[type=password] {
        width: 95%;
        padding: 10px;
        margin-bottom: 15px;
        border-radius: 4px;
        border: 1px solid #ccc;
    }
    form input[type=submit] {
        background-color: #007bff;color: #fff;
        border: none;
        padding: 12px;
        width: 100%;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }
    form input[type=submit]:hover {
        background-color: #0069d9;
    }
</style>
</head>
<body>
	<h1>掲示板へようこそ</h1>
	
	<div class="login-container">
	<h2>ログイン</h2>
	<%
	String error = (String) request.getAttribute("error");
	%>
	<%
	if (error != null) {
	%>
	<p style="color: red;"><%=error%></p>
	<%
	}
	%>
	<form action="Login" method="post">
	ユーザー名:<input type="text" name="name"><br> 
	パスワード:<input type="password" name="plainPass"><br> 
	<input type="submit"value="ログイン">
	<a href="register.jsp" style="display:block; margin-top:10px; text-align:center;">新規登録</a>
	</form>
	</div>
</body>
</html>