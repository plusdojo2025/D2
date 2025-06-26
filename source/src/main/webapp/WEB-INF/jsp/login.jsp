<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン！</title>

<!-- CSS 読み込み（キャッシュ対策） -->
<link rel="stylesheet" href="<c:url value='/css/login.css'/>?v=1.0">
<link rel="stylesheet" href="<c:url value='/css/common.css'/>?v=1.0">
</head>

<body>
  <div class="container">

    <!-- h1からdivに変更して画像拡大を防止 -->
    <div class="logo-header">
      <img src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo" class="logo-img">
    </div>

    <hr>

    <!-- ログインフォーム -->
    <form method="POST" action="<c:url value='/LoginServlet' />"
          onsubmit="return validateLoginForm();">

      <div>
        <label for="id">ID：</label>
        <input type="text" name="id" id="id"><br><br>
      </div>

      <div>
        <label for="pw">パスワード：</label>
        <input type="password" name="pw" id="pw"><br><br>
      </div>

      <input type="submit" name="login" value="ログイン">
    </form>

    <br><br>
    <div>
      <span>新規登録はこちら→ </span>
      <a href="<c:url value='/UserRegistServlet' />">ユーザー登録</a>
    </div>
  </div>

  <!-- JavaScriptファイル読み込み -->
  <script src="<c:url value='/js/login.js' />"></script>
  <script src="<c:url value='/js/common.js' />"></script>

  <!-- サーバーからのエラーがある場合はアラート表示 -->
  <c:if test="${not empty errorMessage}">
    <script>
      alert("${errorMessage}");
    </script>
  </c:if>
</body>
</html>