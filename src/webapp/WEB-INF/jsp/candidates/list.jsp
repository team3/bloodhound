<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script src="${pageContext.request.contextPath}/js/social-services.js"></script>

    <title>Bilberry | Search Results</title>
</head>
<body>
<jsp:include page="../tiles/header.jsp" flush="false"/>

<div class="body-content">
    <p style="color: #1E90FF">Profiles: ${candidates.size()}</p>
    <div class="candidates-content">
        <c:forEach items="${candidates}" var="c" varStatus="status">
            <div class="candidate-block">
                <div class="candidate-block-photo"><a href="/candidates/show/${c.candidateId}"><img src="../images/face.jpg" width="50px" height="50px"/></a></div>
                <div class="candidate-block-details">
                    <div class="candidate-block-details-name"><a href="${pageContext.request.contextPath}/candidates/show/${c.candidateId}">${c.firstName} ${c.secondName}</a></div>
                    <div class="candidate-block-details-country">${c.country}</div>
                    <div class="candidate-block-details-position">Developer</div>
                    <div class="candidate-block-details-skills">
                        ${c.skills}
                    </div>
                    <div class="candidate-block-details-social-buttons">
                        <div class="small-button github-button"
                             onclick="bilberry.showGithubSuggestions($('#matching-social-users-${status.index}'),
                                     '${c.firstName}', '${c.secondName}', '${c.stringLocation}'); return false;"></div>

                        <div class="small-button stackoverflow-button"
                             onclick="bilberry.showStackoverflowSuggestions($('#matching-social-users-${status.index}'),
                                     '${c.firstName}', '${c.secondName}', '${c.stringLocation}'); return false;"></div>
                    </div>

                    <div id = "matching-social-users-${status.index}" style="display: none; width: 400px; border: 1px solid #e2f3ff">

                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
</body>
</html>
