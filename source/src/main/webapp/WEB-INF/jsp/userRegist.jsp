<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>ユーザー登録</title>

	<!-- CSSキャッシュ防止 -->
	<link rel="stylesheet" href="<c:url value='/css/userRegist.css'/>?v=1.0">
	<link rel="stylesheet" href="<c:url value='/css/common.css'/>?v=1.0">
</head>

<body>
	<div class="container">
		

		<div class="logo-header">
			<img src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo" class="logo-img">
		</div>

		<hr>

		<form action="<c:url value='/UserRegistServlet' />" method="post" onsubmit="return validateUserRegistForm();">
			<div>
				<label for="id">ID：</label> 
				<input type="text" id="id" name="id"><br><br>
			</div>

			<div>
				<label for="pw">パスワード：</label> 
				<input type="password" id="pw" name="pw"><br><br>
			</div>

			<div>
				<label for="pwConfirm">パスワード（確認）：</label> 
				<input type="password" id="pwConfirm" name="pwConfirm"><br><br>
			</div>

			<input type="submit" value="登録">
		</form>

		<br>
		<div>
			<a href="<c:url value='/LoginServlet' />">ログインページに戻る</a>
		</div>
	</div>

	<!-- JavaScript読み込み -->
	<script src="<c:url value='/js/userRegist.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>