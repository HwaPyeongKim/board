<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-28
  Time: 오후 12:35
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
  <h2>게시글 상세 보기</h2>
  <div class="board">
    <div class="field">
      <div class="label">작성자</div>
      <div class="text">${board.userid}</div>
    </div>
    <div class="field">
      <div class="label">이메일</div>
      <div class="text">${board.email}</div>
    </div>
    <div class="field">
      <div class="label">조회수</div>
      <div class="text">${board.readcount}</div>
    </div>
    <div class="field">
      <div class="label">작성일</div>
      <div class="text"> <fmt:formatDate value="${board.writedate}" pattern="yyyy.MM.dd hh:mm:ss" /> </div>
    </div>
    <div class="field">
      <div class="label">제목</div>
      <div class="text">${board.title}</div>
    </div>
    <div class="field">
      <div class="label">내용</div>
      <div class="text"><pre>${board.content}</pre></div>
    </div>

    <div class="field">
      <div class="label">이미지</div>
      <div class="text">
        <c:choose>
          <c:when test="${empty board.savefilename}">
            <img src="/images/noname.jpg" width="250" alt="이미지가 없습니다." />
          </c:when>
          <c:otherwise>
            <img src="${board.savefilename}" width="350" alt="${board.image}" />
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <br />
    <div class="login-button">
      <input type="button" value="수정" onclick="location.href='updateBoardForm?num=${board.num}'" />
      <c:if test="${board.userid == loginUser.userid}">
      <input type="button" value="삭제" onclick="deleteBoard('${board.num}')" />
      <input type="button" value="패스워드 수정" onclick="updatePass('${board.num}')" />
      </c:if>
      <input type="button" value="목록" onclick="location.href='main" />
    </div>
  </div>
  <br />
  <div clas="reply">
    <div class="reply_row">
      <div class="reply_col reply_title">작성자</div>
      <div class="reply_col reply_title">작성일</div>
      <div class="reply_col reply_title" style="text-align: center;">댓글</div>
      <c:if test="${reply.userid == loginUser.userid}">
      <div class="reply_col reply_title">수성/삭제</div>
      </c:if>
    </div>

    <form method="post" action="addReply" name="addRep">
      <input type="hidden" name="boardnum" value="${board.num}">
      <input type="hidden" name="userid" value="${loginUser.userid}">
      <div class="reply_row">
        <div class="reply_col">${loginUser.userid}</div>
        <div class="reply_col">
          <c:set var="now" value="<%=new java.util.Date() %>" />
          <fmt:formatDate value="${now}" pattern="yyyy.MM.dd hh:mm" />
        </div>
        <div class="reply_col">
          <input type="text" name="content" size="60" />
        </div>
        <div class="reply_col">
          <input type="submit" value="작성" onclick="return replyCheck()" />
        </div>
      </div>
    </form>
    <br />
    <c:choose>
      <c:when test="${empty replyList}">
        <div class="reply_row">
          <div class="reply_col">등록된 댓글이 없습니다.</div>
        </div>
      </c:when>
      <c:otherwise>
        <c:forEach items="${replyList}" var="reply">
          <div class="reply_row">
            <div class="reply_col">${reply.userid}</div>
            <div class="reply_col"> <fmt:formatDate value="${reply.writedate}" pattern="yyyy.MM.dd hh:mm" /> </div>
            <div class="reply_col"><pre>${reply.content}</pre></div>
            <div class="reply_col">
              <c:if test="${reply.userid == loginUser.userid}">
                <input type="button" value="삭제" onclick="deleteReply('${reply.replynum}','${board.num}')" />
              </c:if>
            </div>
          </div>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>
</div>

</body>
</html>
