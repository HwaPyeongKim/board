<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-28
  Time: 오후 5:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        function selectedimage() {
            document.frm.submit();
        }
    </script>
</head>
<body>

<div id="wrap" align="center">
    <form action="fileupload" method="post" name="frm" enctype="multipart/form-data">
        <h1>파일 선택</h1>
        <input type="file" name="image" onchange="selectedimage()">
    </form>
</div>

</body>
</html>
