<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>ケンコークラフト</title>
<script src="<c:url value='/js/common.js' />"></script>
<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>
<body>
<h1><c:out value="${result.title}" /></h1>
<hr>
<p><c:out value="${result.message}" /></p>

<p>飲酒ポイント: <c:out value="${alcoholMessage}" /></p>
<p>睡眠ポイント: <c:out value="${sleepMessage}" /></p>
<p>摂取カロリーポイント: <c:out value="${calorieMessage}" /></p>
<p>禁煙ポイント: <c:out value="${nosmokeMessage}" /></p>
<a href="${result.backTo}">戻る</a>
</body>
</html>
