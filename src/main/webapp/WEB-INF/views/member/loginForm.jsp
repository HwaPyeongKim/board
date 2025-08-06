<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-25
  Time: 오후 12:12
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
<form action="login" method="post" name="login" class="login-form">
    <h2>Login</h2>
    <div class="field">
        <label for="userid">User ID</label>
        <input type="text" name="userid" id="userid" value="${dto.userid}" />
    </div>
    <div class="field">
        <label for="pwd">Password</label>
        <input type="password" name="pwd" id="pwd"/>
    </div>
    <div class="login-button">
        <input type="submit" value="Login" class="btn-login" />
        <input type="button" value="Join" class="btn-login" onclick="location.href='joinForm'" />
    </div>
    <div>${msg}</div>
    <div class="sns-login">
        <input type="button" class="btn naver" value="Naver" />
        <input type="button" class="btn google" value="Google" />
        <input type="button" class="btn kakao" value="Kakao" onclick="location.href='kakaostart'" />
    </div>
</form>
</body>
</html>
