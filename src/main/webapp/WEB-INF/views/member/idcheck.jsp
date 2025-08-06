<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-25
  Time: 오후 3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function idok(userid) {
            opener.document.join.userid.value = userid;
            opener.document.join.reid.value = userid;
            self.close();
        }
    </script>
</head>
<body>

<form action="idcheck" style="margin: 20px; display: flex; justify-content: center;">
    아이디 : <input type="text" name="userid" value="${userid}" />
    <input type="submit" value="중복체크" />
</form>

<div style="display: flex; justify-content: center;">
    <c:choose>
        <c:when test="${result==1}">
            ${userid} 는(은) 사용 가능한 아이디입니다.
            <input type="button" value="사용" onclick="idok('${userid}')" />
        </c:when>
        <c:otherwise>
            <script type="text/javascript">
                opener.document.join.userid.value = "";
                opener.document.join.reid.value = "";
            </script>
            ${userid} 는(은) 이미 사용중인 아이디입니다.
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>