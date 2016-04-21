<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
  <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Add vacancy</title>

</head>
<body>
<jsp:include page="../tiles/header.jsp" flush="true"/>

<jsp:include page="../tiles/footer.jsp" />
</body>
</html>
