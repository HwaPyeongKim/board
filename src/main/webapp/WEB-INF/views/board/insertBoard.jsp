<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-28
  Time: 오후 4:13
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

<div id="main_container">
    <h2>게시글 등록</h2>
    <div class="board">
        <form method="post" action="insertBoard" name="insertBoard" class="insertBoard">
            <div class="field">
                <label for="userid">작성자</label>
                <input type="text" name="userid" value="${loginUser.userid}" readonly />
            </div>

            <div class="field">
                <label for="pass">비밀번호</label>
                <input type="password" name="pass" />
            </div>

            <div class="field">
                <label for="email">이메일</label>
                <input type="email" name="email" value="${loginUser.email}" />
            </div>

            <div class="field">
                <label for="title">제목</label>
                <input type="text" name="title" value="${dto.title}" />
            </div>

            <div class="field">
                <label for="content">내용</label>
                <textarea name="content" rows="10" cols="1000">${dto.content}</textarea>
            </div>

            <div class="field">
                <label for="uploadImage">이미지</label>
                <input type="button" value="이미지 선택" onclick="selectImg()" />
            </div>

            <div class="field">
                <label>이미지 미리보기</label>

                <c:choose>
                    <c:when test="${empty dto.savefilename}">
                        <div style="flex: 4;">
                            <img src="" id="previewing" width="150" style="display: none;">
                        </div>
                        <input type="hidden" name="image" readonly />
                        <input type="hidden" name="savefilename" readonly />
                    </c:when>
                    <c:otherwise>
                        <div style="flex: 4;">
                            <img src="/images/${dto.savefilename}" id="previewing" width="150">
                        </div>
                        <input type="hidden" name="image" value="${dto.image}" readonly />
                        <input type="hidden" name="savefilename" value="${dto.savefilename}" readonly />
                    </c:otherwise>
                </c:choose>

            </div>

            <div class="login-button">
                <input type="submit" value="작성완료" />
                <input type="button" value="목록" onclick="location.href='main'" />
            </div>

            <div>${msg}</div>
        </form>
    </div>
</div>

</body>
</html>
