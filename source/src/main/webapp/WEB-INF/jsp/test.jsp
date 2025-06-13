<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- ここから各々のjspファイル中の街並みアバターを表示したい場所にコピー -->
<!-- 同じようにTestServlet.javaにもコピー項目がある -->

<!-- jsファイル内で加工するためここでは非表示--------------------------------- -->
<c:forEach var="imgPathBuild" items="${elmsImage.imgPathSetBuild}">
	<img src="${imgPathBuild}" class="imgBuild" style="display: none;">
</c:forEach>
<c:forEach var="imgPathCloth" items="${elmsImage.imgPathSetCloth}">
	<img src="${imgPathCloth}" class="imgCloth" style="display: none;">
</c:forEach>
<img src="${elmsImage.imgPathFace}" id="imgFace" style="display: none;">
<img src="${elmsImage.imgPathPeople}" id="imgPeople" style="display: none;">
<input type="hidden" id="peopleNum" value="${elmsImage.peopleNum}">
<!-- --------------------------------------------------------------- -->

<c:set var="width" value="500" /> <!-- 実際に画面に表示する際の横幅[px] -->
<c:set var="height" value="500" /> <!-- 実際に画面に表示する際の高さ[px] -->
<canvas id="myCanvas" width="2000" height="2000" style="width:${width}px; height:${height}px;"></canvas>

<!-- ここまでコピー -->

</body>

<script src="js/createTownAvatar.js"></script>

</html>