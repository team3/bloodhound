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

<div class="body-content">
    <h2>Add Vacancy</h2>

    <s:form action="/vacancies/add" method="POST" commandName="vacancy">
        <table>
            <tr>
                <td><s:input path="name" id="vacancy_name" maxlength="500" size="120" placeholder="vacancy title"/></td>
            </tr>
            <tr>
                <td>
                    <input type = "text" id="vacancy_location" size="120"/>
                    <s:input path="stringLocation" id="vacancy_location_position" maxlength="500" size="120" cssStyle="display: none"/>
                </td>
            </tr>
            <tr>
                <td><s:input path="skills" id="vacancy_skills" maxlength="500" size="120" placeholder="skills"/>(+)</td>
            </tr>
            <tr>
                <td><s:textarea path="responsibilities" id="vacancy_responsibilities" maxlength="4000" cols="150" rows="20"
                        placeholder="responsibilities"/></td>
            </tr>
            <tr>
                <td><div class="btn btn-submit" onclick="$('#vacancy')[0].submit(); return false;">Submit</div>
                    <div class="btn btn-reset" onclick="$('#vacancy')[0].reset(); return false;">Reset</div>
                </td>
            </tr>
        </table>
    </s:form>
</div>

<script>
    function initAutocomplete() {
        var autocomplete = new google.maps.places.Autocomplete(document.getElementById('vacancy_location'), {types: ['geocode']});
        autocomplete.addListener('place_changed', function () {
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                window.alert("Autocomplete's returned place contains no geometry");
                return;
            } else {
                console.log(place.geometry.location);
                $("#vacancy_location_position").val(place.geometry.location.lat() + ',' + place.geometry.location.lng());
            }
        });
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBX8ViL1KgtaaAWXauAFkaQgyYxHcxFno0&signed_in=true&libraries=places&callback=initAutocomplete"
        async defer></script>

<jsp:include page="../tiles/footer.jsp" />
</body>
</html>
