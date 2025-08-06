<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-25
  Time: 오후 2:54
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
    <h2>Board List</h2>
    <div class="logininfo">
        <div class="user">
            ${loginUser.userid}(${loginUser.name}) 님이 로그인 하셨습니다.
            <input type="button" value="회원정보수정" onclick="location.href='updateMemberForm'" />
            <input type="button" value="로그아웃" onclick="location.href='logout'" />
            <input type="button" value="회원탈퇴" onclick="deleteMember(${loginUser.userid})" />
        </div>

        <div class="writebutton">
            <input type="button" value="글 작성" onclick="location.href='insertBoardForm'" />
        </div>
    </div>

    <div class="board">
        <div class="title_row">
            <div class="title_col">번호</div>
            <div class="title_col">제목</div>
            <div class="title_col">작성자</div>
            <div class="title_col">작성일</div>
            <div class="title_col">조회수</div>
        </div>

        <c:forEach items="${boardList}" var="board">
            <div class="row">
                <div class="col">${board.num}</div>
                <div class="col">
                    <a href="boardView?num=${board.num}">
                        ${board.title}
                        <c:if test="${board.replycnt > 0}">
                            <b style="color: red;">${board.replycnt}</b>
                        </c:if>
                        <c:if test="${not empty board.savefilename}">
                            <b style="color: blue;">[img]</b>
                        </c:if>
                    </a>
                </div>
                <div class="col">${board.userid}</div>
                <div class="col"> <fmt:formatDate value="${board.writedate}" pattern="yyyy.MM.dd" /> </div>
                <div class="col">${board.readcount}</div>
            </div>
        </c:forEach>

        <div class="paging">
            <c:if test="${paging.prev}">
                <a href="main?page=${paging.beginPage - 1}">Prev</a>
            </c:if>
            <c:forEach begin="${paging.beginPage}" end="${paging.endPage}" var="index">
                <a href="main?page=${index}"<c:if test="${paging.page == index}"> class="on"</c:if>>${index}</a>
            </c:forEach>
            <c:if test="${paging.next}">
                <a href="main?page=${paging.endPage + 1}">Next</a>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
