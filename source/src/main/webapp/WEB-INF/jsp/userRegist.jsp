<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
</head>
<body>

	<hr>


	<h2>ユーザー登録フォーム</h2>

	<form action="UserRegistServlet" method="post">
		<label for="id">ID：</label> 
		<input type="text" id="id" name="id"><br>
		<br> <label for="pw">パスワード：</label> 
		<input type="password"id="pw" name="pw"><br>
		<br> <label for="pwConfirm">パスワード（確認）：</label> 
		<input type="password" id="pwConfirm" name="pwConfirm"><br>
		<br> 
		<input type="submit" value="登録" onclick="return confirm('お間違えないでしょうか？');">
	</form>

	<br>
	<a href="LoginServlet">ログインページに戻る</a>
	
	<!-- パスワードの2重チェック処理をservletから移行 -->

</body>
</html>