<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ログイン！</title>
	<link rel="stylesheet" href="<c:url value='/css/login.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>

<body>
    <h1>ログイン</h1>

    <hr>
    <!-- JavaScriptでチェックをかける -->
    <form method="POST" action="<c:url value='/LoginServlet' />" onsubmit="return validateLoginForm();">
        <label for="id">ID：</label>
        <input type="text" name="id" id="id"><br><br>

        <label for="pw">パスワード：</label>
        <input type="password" name="pw" id="pw"><br><br>

        <input type="submit" name="login" value="ログイン">
    </form>

    <br>
    <a>新規登録はこちら→ </a>
    <a href="<c:url value='/UserRegistServlet' />">ユーザー登録</a>
    
    <!-- JavaScriptファイル読み込み -->
	<script src="<c:url value='/js/login.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>
