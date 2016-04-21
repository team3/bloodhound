<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Bilberry-search | Candidate</title>
</head>
<body>

<jsp:include page="../tiles/header.jsp" flush="false"/>

<div class="body-content">
    <div class="candidate-profile">
        <div class="candidate-profile-photo">
            <img src="../images/face.jpg"/>
        </div>
        <div class="candidate-profile-links with-border">

        </div>
        <div class="candidate-profile-basic-info">
            <p style="color: #1E90FF; font-size: medium"><b>${candidate.firstName} ${candidate.secondName}</b></p>

            <p style="color: #6f6f6f; font-style: italic; font-size: small">Senior Java Developer</p>
            <p style="color: #6f6f6f; font-style: italic; font-size: x-small">${candidate.stringLocation}</p>
        </div>
        <p style="color: #6f6f6f; font-style: italic; font-size: x-small">${candidate.skills}</p>
    </div>

    <p style="color: #C71585; font-size: medium">Matching vacancies</p>
    <jsp:include page="../tiles/vacancies-list.jsp" flush="true">
        <jsp:param name="v" value="${vacancies}" />
    </jsp:include>
</div>


</body>
<jsp:include page="../tiles/footer.jsp" flush="false"/>

</html>
