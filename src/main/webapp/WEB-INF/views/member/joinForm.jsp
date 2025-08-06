<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-25
  Time: 오후 3:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script src="/scripts/board.js"></script>
</head>
<body>

<form action="join" method="post" name="join" class="login-form">
    <h2>Join</h2>
    <div class="field">
        <label for="userid">User ID</label>
        <input type="text" name="userid" id="userid" value="${dto.userid}" style="flex: 3;" />
        <input type="button" value="중복체크" onclick="idcheck()" style="flex: 1;" />
        <input type="hidden" name="reid" value="${reid}" />
    </div>
    <div class="field">
        <label for="pwd">Password</label>
        <input type="password" name="pwd" id="pwd"/>
    </div>
    <div class="field">
        <label for="pwd_check">Retype Password</label>
        <input type="password" name="pwd_check" id="pwd_check"/>
    </div>
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
        <input type="submit" value="Join" class="btn-login" />
        <input type="button" value="Back" class="btn-login" onclick="location.href='/" />
    </div>
    <div>${msg}</div>
</form>
</body>
</html>
