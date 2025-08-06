<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-29
  Time: 오후 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
    alert("비밀번호가 변경되었습니다.");
    opener.document.location.href="boardViewWithoutCnt?num=${num}";
    self.close();
</script>
</body>
</html>
