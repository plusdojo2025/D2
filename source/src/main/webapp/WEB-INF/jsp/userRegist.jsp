<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ユーザー登録</title>
	<link rel="stylesheet" href="<c:url value='/css/userRegist.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>

<body>
	<hr>

	<h2>ユーザー登録フォーム</h2>

	<form action="<c:url value='/UserRegistServlet' />" method="post" onsubmit="return validateUserRegistForm();">
		<label for="id">ID：</label> 
		<input type="text" id="id" name="id"><br><br>

		<label for="pw">パスワード：</label> 
		<input type="password" id="pw" name="pw"><br><br>

		<label for="pwConfirm">パスワード（確認）：</label> 
		<input type="password" id="pwConfirm" name="pwConfirm"><br><br>

		<input type="submit" value="登録">
	</form>

	<br>
	<a href="<c:url value='/LoginServlet' />">ログインページに戻る</a>
	
	<script src="<c:url value='/js/userRegist.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>

</body>
</html>