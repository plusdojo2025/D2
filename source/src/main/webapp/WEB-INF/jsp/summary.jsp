<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>12か月サマリー</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/summary.css" />
</head>
<body>
  <h1><a href="HomeServlet">ケンコークラフト</a></h1>
  <h2>12か月サマリー</h2>

  <script>
    // 月リスト (例: [6,5,4,...]) 今月から逆順
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
        <h3>${monthList[i]}月</h3>
        <c:choose>
          <c:when test="${avatar != null}">
            <c:forEach var="b" items="${avatar.buildings}">
              <img src="${b.imagePath}" alt="建物" />
            </c:forEach>
            <c:if test="${avatar.cloth != null}"><img src="${avatar.cloth.imagePath}" alt="服" /></c:if>
            <c:if test="${avatar.shoe != null}"><img src="${avatar.shoe.imagePath}" alt="靴" /></c:if>
            <c:if test="${avatar.hat != null}"><img src="${avatar.hat.imagePath}" alt="帽子" /></c:if>
            <c:if test="${avatar.costume != null}"><img src="${avatar.costume.imagePath}" alt="コスチューム" /></c:if>
            <img src="${avatar.face.imagePath}" alt="顔" />
            <c:forEach var="n" begin="1" end="${avatar.peopleCount}">
              <img src="${avatar.peopleImage.imagePath}" alt="人物" />
            </c:forEach>
          </c:when>
          <c:otherwise><p>データなし</p></c:otherwise>
        </c:choose>
      </div>
    </c:forEach>
  </div>

  <a href="HistoryServlet">1年以上前の街並みはこちら</a>

  <div style="text-align:center; margin-top:40px;">
    <button id="prev-btn">← 前の月</button>
    <span id="month-display" style="margin:0 15px; font-weight:bold; font-size:24px;"></span>
    <button id="next-btn">次の月 →</button>
    <br /><br />
    <canvas id="myCanvas" width="1000" height="800" style="border:1px solid black; margin-top:10px;"></canvas>
  </div>

  <script>
    // avatarDataList生成
    const avatarDataList = [
      <c:forEach var="avatar" items="${avatars}" varStatus="status">
        {
          month: ${monthList[status.index]},
          face: "${avatar != null && avatar.face != null ? avatar.face.imagePath : ''}",
          cloth: "${avatar != null && avatar.cloth != null ? avatar.cloth.imagePath : ''}",
          shoe: "${avatar != null && avatar.shoe != null ? avatar.shoe.imagePath : ''}",
          hat: "${avatar != null && avatar.hat != null ? avatar.hat.imagePath : ''}",
          costume: "${avatar != null && avatar.costume != null ? avatar.costume.imagePath : ''}",
          buildings: [<c:forEach var="b" items="${avatar.buildings}" varStatus="bs">${bs.index > 0 ? ',' : ''}"${b.imagePath}"</c:forEach>],
          peopleImage: "${avatar != null && avatar.peopleImage != null ? avatar.peopleImage.imagePath : ''}",
          peopleCount: ${avatar != null ? avatar.peopleCount : 0}
        }<c:if test="${!status.last}">,</c:if>
      </c:forEach>
    ];
  </script>

  <script src="${pageContext.request.contextPath}/js/summary.js"></script>
</body>
</html>