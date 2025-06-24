<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>12か月サマリー</title>
  <link rel="stylesheet" href="<c:url value='/css/summary.css'/>">
  <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>
<body>
  <h1><a href="<c:url value='/HomeServlet' />">ケンコークラフト</a></h1>
  <h2>12か月サマリー</h2>

  <script>
    const monthList = [
      <c:forEach var="m" items="${monthList}" varStatus="status">
        ${m}<c:if test="${!status.last}">,</c:if>
      </c:forEach>
    ];
  </script>

  <div class="container">
    <c:forEach var="i" begin="0" end="11" varStatus="status">
  <c:set var="avatar" value="${avatars[i]}" />
  <div class="month-block">
    <c:choose>
      <c:when test="${avatar != null}">
        <h3>${yearList[i]}年 ${monthList[i]}月</h3>
        <canvas class="imageCanvas" id="canvas_${yearList[i]}_${monthList[i]}" width="1627" height="1021" style="width:300px;"></canvas>
      </c:when>
      <c:otherwise>
        <h3>${yearList[i]}年 ${monthList[i]}月（データなし）</h3>
      </c:otherwise>
    </c:choose>
  </div>
</c:forEach>
  </div>

  <a href="<c:url value='/HistoryServlet' />">1年以上前の街並みはこちら</a>

  <div style="text-align:center; margin-top:40px;">
    <button id="prev-btn">← 前の月</button>
    <span id="month-display" style="margin:0 15px; font-weight:bold; font-size:24px;"></span>
    <button id="next-btn">次の月 →</button>
    <br /><br />
    <div id="avatar-container"></div>
  </div>

  <script>
    const taList = {};
    const yearMonthList = [
        <c:forEach var="i" begin="0" end="11" varStatus="status">
          { year: ${yearList[i]}, month: ${monthList[i]} }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
      ];

    <c:forEach var="avatar" items="${avatars}">
      <c:if test="${avatar != null}">
        taList["${avatar.year}-${avatar.month}"] = {
          buildPaths: [<c:forEach var="build" items="${avatar.buildings}">"${build.imagePath}",</c:forEach>],
          peopleCount: ${avatar.peopleCount},
          peoplePath: "${avatar.peopleImage.imagePath}",
          clothPath: "${avatar.cloth.imagePath}",
          shoePath: "${avatar.shoe.imagePath}",
          hatPath: "${avatar.hat.imagePath}",
          costumePath: "${avatar.costume.imagePath}",
          facePath: "${avatar.face.imagePath}"
        };
        yearMonthList.push({
          year: ${avatar.year},
          month: ${avatar.month}
        });
      </c:if>
    </c:forEach>
  </script>

  <script src="<c:url value='/js/test.js' />"></script>
  <script src="<c:url value='/js/summary.js' />"></script>
  <script src="<c:url value='/js/common.js' />"></script>
</body>
</html>