<%--
  Created by IntelliJ IDEA.
  User: JAVA
  Date: 2025-07-28
  Time: 오후 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
  opener.document.getElementById('previewing').setAttribute('src','${savefilename}');
  opener.document.getElementById('previewing').style.display='inline';

  opener.document.insertBoard.image.value='${image}';
  opener.document.insertBoard.savefilename.value='${savefilename}';
  self.close();
</script>
</body>
</html>
