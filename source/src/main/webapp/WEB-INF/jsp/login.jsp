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
	<form method="POST" action="/D2/LoginServlet"> <!-- // TODO: /D2/...に変更 -->
		<label for="id">ID：</label> <input type="text" name="id"><br>
		<br> <label for="pw">パスワード：</label> <input type="password"
			name="pw"><br> <br> <input type="submit"
			name="login" value="ログイン">
	</form>
	
	<a href="UserRegistServlet">ユーザー登録</a>
	
	<!-- ID・パスワードの入力チェック処理をservletから移行 -->
</body>
</html>