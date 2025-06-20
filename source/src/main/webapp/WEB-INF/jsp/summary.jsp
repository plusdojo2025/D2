<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.TownAvatarElements" %>
<%
    List<TownAvatarElements> avatars = (List<TownAvatarElements>) request.getAttribute("avatars");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>summary</title>
      <style>
        .container {
            display: flex;
            flex-wrap: wrap;
            width: 100%;
            max-width: 1200px;
            margin: 0 auto;
        }

        .month-block {
            width: 25%; /* 4列表示 */
            box-sizing: border-box;
            padding: 10px;
            border: 1px solid #ccc;
            text-align: center;
        }

        .img-preview {
            width: 100px;
            height: 100px;
            object-fit: contain;
            display: block;
            margin: 5px auto;
        }
    </style>
</head>
</head>
<body>
<h1>
			<a href="HomeServlet">ケンコークラフト</a>
		</h1>
    <h2>サマリー</h2>

    
    <div class="container">
        <c:forEach var="avatar" items="${avatars}" varStatus="status">
            <div class="month-block">
                <h3>${monthList[status.index]}月</h3>

                <c:choose>
                    <c:when test="${avatar != null}">
                        <c:if test="${avatar.costume != null}">
                            <img src="${avatar.costume.imagePath}" class="img-preview" alt="costume" />
                        </c:if>
                        <c:if test="${avatar.costume == null}">
                            <c:if test="${avatar.cloth != null}">
                                <img src="${avatar.cloth.imagePath}" class="img-preview" alt="cloth" />
                            </c:if>
                            <c:if test="${avatar.shoe != null}">
                                <img src="${avatar.shoe.imagePath}" class="img-preview" alt="shoe" />
                            </c:if>
                            <c:if test="${avatar.hat != null}">
                                <img src="${avatar.hat.imagePath}" class="img-preview" alt="hat" />
                            </c:if>
                        </c:if>

                        <c:if test="${avatar.face != null}">
                            <img src="${avatar.face.imagePath}" class="img-preview" alt="face" />
                        </c:if>

                        <c:forEach var="b" items="${avatar.buildings}">
                            <img src="${b.imagePath}" class="img-preview" alt="building" />
                        </c:forEach>

                        <c:if test="${avatar.peopleImage != null}">
                            <img src="${avatar.peopleImage.imagePath}" class="img-preview" alt="people" />
                        </c:if>

                        <p>禁煙人数: ${avatar.peopleCount}</p>
                    </c:when>

                    <c:otherwise>
                        <p>データなし</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
  
    <a href="HistoryServlet">1年以上前の街並みはこちら</a>
</body>
</html>