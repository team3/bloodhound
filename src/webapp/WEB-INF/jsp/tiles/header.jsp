<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="page-header">
        <div style="float: right;">
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                ${pageContext.request.userPrincipal.name} | <c:url value="login?logout" var="logoutUrl"/><a
                    href="${logoutUrl}">Log Out</a>
            </c:if>

            <br/>
            <img src="${pageContext.request.contextPath}/images/UA.png" width="24px" height="16px"
                 style="border: 1px solid azure;"/>
            <img src="${pageContext.request.contextPath}/images/PL.png" width="24px" height="16px"
                 style="border: 1px solid azure;"/>
        </div>
        <div style="float: left;">
            <a href="/candidates/search"><img src="${pageContext.request.contextPath}/images/Blueberry.png" width="40" height="40"
                 style="float: left"/></a>

            <p style="font-size: large; color: crimson; float: left; width: 65px;">BilBerry </p>

            <p style="font-size: smaller; color: dodgerblue; float: left;">search</p>
        </div>
    </div>
</header>