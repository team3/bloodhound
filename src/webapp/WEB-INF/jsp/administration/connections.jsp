<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bilberry | Configuration</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/social-services.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<jsp:include page="../tiles/header.jsp" flush="false" />

<div class="body-content">
    <jsp:include page="../auth/search-engines-connections.jsp" flush="false">
        <jsp:param name="isLinkedinConnected" value="${isLinkedinConnected}"/>
        <jsp:param name="isFacebookConnected" value="${isFacebookConnected}"/>
        <jsp:param name="isGithubConnected" value="${isGithubConnected}" />
        <jsp:param name="isStackoverflowConnected" value="${isStackoverflowConnected}" />
    </jsp:include>
</div>
<jsp:include page="../tiles/footer.jsp" flush="false" />

</body>
</html>
