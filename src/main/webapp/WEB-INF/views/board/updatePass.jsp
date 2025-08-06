<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-29
  Time: 오후 1:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="updatePass" method="post" style="margin: 20px;">
    <h1>패스워드 수정</h1>
    <input type="hidden" name="num" value="${num}">
    <div>
        <label for="pass">기본 비밀번호</label>
        <input type="password" name="oldPass" />
    </div>
    <div>
        <label for="pass">새 비밀번호</label>
        <input type="password" name="newPass" />
    </div>
    <div>
        <label for="pass">새 비밀번호 확인</label>
        <input type="password" name="confirmPass" />
    </div>
    <input type="submit" value="비밀번호 변경" />
    <div>${msg}</div>
</form>

</body>
</html>
