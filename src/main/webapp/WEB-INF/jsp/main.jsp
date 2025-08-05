<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="model.User,model.Tweet,java.util.List,java.time.format.DateTimeFormatter"%>
<%
//セッションスコープに保存されたユーザー情報を取得
String userName = (String) session.getAttribute("userName");
//リクエストスコープに保存されたユーザー情報の取得
List<Tweet> tweetList = (List<Tweet>) request.getAttribute("tweetList");

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>掲示板</title>
<style>
body {
	background-color: #e9ecef;
	margin: 0;
	padding: 20px;
}

h1 {
	text-align: center;
	color: #333;
}

.group {
	max-width: 600px;
	width: 100%;
	margin: 5px auto;
	padding: 0 20px;
	box-sizing: border-box;
}

.form-group {
	display: flex;
	gap: 10px;
	margin: 20px auto;
}

textarea {
	width: 100%;
    max-width: 600px;
    padding: 10px;
    border-radius: 4px;
    border: 1px solid #ccc;
    font-size: 16px;
    resize: vertical;
    box-sizing: border-box;
}

form input[type=submit] {
	background-color: #007bff;
	color: #fff;
	border: none;
	padding: 12px;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

form input[type=submit]:hover {
	background-color: #0069d9;
}
.post-box {
  max-width: 600px; 
  padding: 10px;
  background: #fff;
  border-radius: 4px;
  border: 1px solid #ccc;
  white-space: pre-wrap;  
  word-wrap: break-word;  
  box-sizing: border-box;
  line-height: 1.2;
  margin: 5px auto;
  text-align: left;
  font-size: 14px;
}

.username {
  margin: 0;
  font-weight: bold;
}

.tweet-text {
  margin-bottom: 10px;
}

.timestamp {
  font-size: 80%;
  color: #666;
  text-align: right;
}

</style>
</head>
<body>
	<h1>掲示板</h1>
	<div class="group">
		<p>
			<%=userName%>さん、ログイン中<br> <a href="Logout">ログアウト</a>
		</p>

		<%
            String error = (String) request.getAttribute("error");
        %>
		<%if (error != null) {%>
		<p style="color: red;"><%=error%></p>
		<%}%>

		<form action="Main" method="post">
			<div class="form-group">
				<textarea name="text" rows="3" placeholder="投稿内容を入力してください"></textarea>
				<input type="submit" value="投稿">
			</div>
		</form>

		<%
		for (Tweet tweet : tweetList) {
		%>
		<div class="post-box">
		    <p class="username"><%= tweet.getUserName() %>：</p>
			<div class="tweet-text"><%=tweet.getText()%></div>
			<div class="timestamp">投稿日時:<%=tweet.getPostTime().format(formatter)%></div>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>