<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン！</title>

<!-- JavaScriptファイル読み込み -->
<script src="js/login.js"></script>
</head>
<body>
    <h1>ログイン</h1>

    <hr>
    <!-- JavaScriptでチェックをかける -->
    <form method="POST" action="${pageContext.request.contextPath}/LoginServlet" onsubmit="return validateLoginForm();">
        <label for="id">ID：</label>
        <input type="text" name="id" id="id"><br><br>

        <label for="pw">パスワード：</label>
        <input type="password" name="pw" id="pw"><br><br>

        <input type="submit" name="login" value="ログイン">
    </form>

    <br>
    <a>新規登録はこちら→ </a>
    <a href="UserRegistServlet">ユーザー登録</a>
</body>
</html>
