<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>ログイン！</title>
</head>
<body>
	<h1>ログイン</h1>

	<hr>
	<form method="POST" action="/D2/LoginServlet">
		ID<input type="text" name="id"><br> <br> 
		PW<input type="password" name="pw"><br> <br> 
		<input type="submit" name="login" value="ログイン">
	</form>
</body>
</html>