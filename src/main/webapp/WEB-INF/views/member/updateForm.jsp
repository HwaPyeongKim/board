<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-28
  Time: 오후 2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script src="/scripts/board.js"></script>
</head>
<body>

<form action="updateMember" method="post" name="updateMember" class="login-form">
    <input type="hidden" name="provider" value="${dto.provider}" />
    <h2>Update</h2>
    <div class="field">
        <label for="userid">User ID</label>
        <input type="text" name="userid" id="userid" value="${dto.userid}" readonly />
    </div>
    <c:choose>
        <c:when test="${empty dto.provider}">
            <div class="field">
                <label for="pwd">Password</label>
                <input type="password" name="pwd" id="pwd"/>
            </div>
            <div class="field">
                <label for="pwd_check">Retype Password</label>
                <input type="password" name="pwd_check" id="pwd_check"/>
            </div>
        </c:when>
        <c:otherwise>
            <div class="field">
                <label for="pwd">Password</label>
                <input type="password" name="pwd" id="pwd" readonly />
            </div>
            <div class="field">
                <label for="pwd_check">Retype Password</label>
                <input type="password" name="pwd_check" id="pwd_check" readonly />
            </div>
        </c:otherwise>
    </c:choose>
    <div class="field">
        <label for="name">Name</label>
        <input type="text" name="name" id="name" value="${dto.name}" />
    </div>
    <div class="field">
        <label for="email">Email</label>
        <input type="text" name="email" id="email" value="${dto.email}" />
    </div>
    <div class="field">
        <label for="phone">Phone</label>
        <input type="text" name="phone" id="phone" value="${dto.phone}" />
    </div>
    <div class="login-button">
        <input type="submit" value="Update" class="btn-login" />
        <input type="button" value="Back" class="btn-login" onclick="location.href='main" />
    </div>
    <div>${msg}</div>
</form>

</body>
</html>
