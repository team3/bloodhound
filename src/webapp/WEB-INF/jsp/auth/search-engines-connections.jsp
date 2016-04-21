<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="connection-block linkedin-connection-block"
     <c:if test="${!param.isLinkedinConnected}">onclick="bilberry.openLinkedinConnector(); return false;"</c:if>
        >
    <label style="position: absolute; bottom: 5px;">
        <input type="radio" <c:if test="${param.isLinkedinConnected}"> checked </c:if> />
    </label>
</div>

<div class="connection-block facebook-connection-block"
     <c:if test="${!param.isFacebookConnected}">onclick="bilberry.openFacebookConnector(); return false;" </c:if>
        >
    <label style="position: absolute; bottom: 5px;">
        <input type="radio" <c:if test="${param.isFacebookConnected}"> checked </c:if> />
    </label>
</div>

<div class="connection-block stackoverflow-connection-block"
     <c:if test="${!param.isStackoverflowConnected}">onclick="bilberry.openStackoverflowConnector(); return false;" </c:if>
        >
    <label style="position: absolute; bottom: 5px;">
        <input type="radio" <c:if test="${param.isStackoverflowConnected}"> checked </c:if> />
    </label>
</div>

<div class="connection-block github-connection-block"
     <c:if test="${!param.isGithubConnected}">onclick="bilberry.openGithubConnector(); return false;" </c:if>
        >
    <label style="position: absolute; bottom: 5px;">
        <input type="radio" <c:if test="${param.isGithubConnected}"> checked </c:if> />
    </label>
</div>