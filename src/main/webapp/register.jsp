<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
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
        width: 100%;
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
<h1>新規登録</h1>
<div class="login-container">
<%
String error = (String) request.getAttribute("error");
%>
<%if (error != null) {%>
<p style="color: red;"><%=error%></p>
<%}%>
<form id="registerForm" action="Register" method="post">
    ユーザー名: <input type="text" id="username" name="name" placeholder="1～20文字で作成"><br>
    パスワード: <input type="password" id="password" name="pass" placeholder="4～20文字で作成"><br>
    <input type="submit" value="登録" id="submitBtn">
</form>
</div>

<script>
    document.getElementById("username").addEventListener("keydown", function(e) {
        if (e.key === "Enter") {
            e.preventDefault(); 
            document.getElementById("password").focus();
        }
    });
    document.getElementById("password").addEventListener("keydown", function(e) {
        if (e.key === "Enter") {
            e.preventDefault(); 
        }
    });
    document.getElementById("submitBtn").addEventListener("click", function() {
        document.getElementById("registerForm").submit();
    });
</script>
</body>
</html>