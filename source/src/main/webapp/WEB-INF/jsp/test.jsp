<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	th, td {
		border: 1px solid #999;
	}   
</style>

</head>
<body>


	<c:forEach var="i" begin="0" end="16" varStatus="status">
		<c:set var="avatar" value="${avatars[i]}" />
        <c:set var="width" value="500" />
        <canvas class="imageCanvas" id="canvas_${avatar.year}_${avatar.month}" width="1627" height="1021" style="width:${width}px;"></canvas>
    </c:forEach>

</body>

	<script>
	    const taList = {}; // タウンアバターのリスト
		const yearMonthList = [];
		<c:forEach var="avatar" items="${avatars}">
			if(!taList[`${avatar.year}-${avatar.month}`]) {
				taList[`${avatar.year}-${avatar.month}`] = [];
			}
			taList[`${avatar.year}-${avatar.month}`] = {
				buildPaths: [<c:forEach var="build" items="${avatar.buildings}">
					"${build.imagePath}",</c:forEach>],
				peopleCount: ${avatar.peopleCount},
				peoplePath: "${avatar.peopleImage.imagePath}",
				clothPath: "${avatar.cloth.imagePath}",
				shoePath: "${avatar.shoe.imagePath}",
				hatPath: "${avatar.hat.imagePath}",
				costumePath: "${avatar.costume.imagePath}",
				facePath: "${avatar.face.imagePath}"
			}

			yearMonthList.push({
				year: ${avatar.year},
				month: ${avatar.month},
			});
		</c:forEach>;
	</script>
	
	<script src="<c:url value='/js/test.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>

</html>