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

Value: ${value}<br/>
Status: ${status}
<s:form action="/swapper/compile" method="post" id="swapper-form" commandName="swapperFormModel">
    <s:textarea path="source" cols="100" rows="20" />

    <div class="btn btn-submit" onclick="$('#swapper-form')[0].submit(); return false;">Submit</div>
</s:form>

</body>
</html>
