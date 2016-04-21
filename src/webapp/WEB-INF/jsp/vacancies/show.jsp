<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search page</title>
</head>
<jsp:include page="../tiles/header.jsp" flush="false"/>
<body>
<div class="body-content">

    <p class="vacancy-content-name">${vacancy.name}</p>

    <p class="vacancy-content-location">${vacancy.stringLocation}</p>

    <b> Skills required </b>
    <p class="vacancy-content-skills">${vacancy.skillsMatrix()}</p>

    <b> Responsibilities</b>
    <p class="vacancy-content-responsibilities">${vacancy.responsibilities}</p>

</div>

</body>
<jsp:include page="../tiles/footer.jsp" flush="false"/>

</html>
