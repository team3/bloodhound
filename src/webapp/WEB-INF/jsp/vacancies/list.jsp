<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://maps.googleapis.com/maps/api/js"></script>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script>
        var map;
        var markers = {};

        function initialize() {
            var mapCanvas = document.getElementById('map');
            var mapOptions = {
                center: new google.maps.LatLng(50.450091, 30.523415),
                zoom: 3,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(mapCanvas, mapOptions)

            show_vacancies_positions();
        }

        google.maps.event.addDomListener(window, 'load', initialize);

        function show_vacancy_details(trow) {
            var location = $(trow).find('td.location').text();
            console.log(location);
            var position = parseGeoPosition(location);
            if (position in markers) {
                var marker = markers[position];

                console.log(marker);
                map.setCenter(marker.getPosition());
                map.setZoom(8);
            }
        }

        function show_vacancies_positions() {
            $('table#vacancies-data').find('tr').each(function (index, tr) {
                var location = $(tr).find('td.location').text();
                var label = $(tr).find('td.vacancy_title').text();

                if (location) {
                    var position = parseGeoPosition(location);
                    var marker = new google.maps.Marker({
                        position: position,
                        map: map,
                        label: label
                    });

                    markers[position] = marker;
                }
            });
        }

        function parseGeoPosition(position) {
            var loc = position.split(",");

            return {lat: parseInt(loc[0]), lng: parseInt([loc[1]])};
        }

    </script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search page</title>
</head>
<body>

<jsp:include page="../tiles/header.jsp" flush="false"/>
<div class="body-content">
    <h4>Vacancies (${vacancies.size()})</h4>
    <table class="bordered-table" id="vacancies-data">
        <tr>
            <th>#</th>
            <th>Name</th>
            <th width="60%">Skills</th>
            <th width="10%">Location</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${vacancies}" var="e">
            <%--<tr onclick='show_vacancy_details(this); return false;'>--%>
            <tr>
                <td><a href="/vacancies/show/${e.id}">@</a></td>
                <td class="vacancy_title">${e.name}</td>
                <td>${e.skillsMatrix()}</td>
                <td class="address">${e.address}</td>
                <td class="location" style="display: none">${e.stringLocation}</td>
                <td><a href="${pageContext.request.contextPath}../vacancies/delete/${e.id}">†</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/>

    <div class="control-buttons-bar">
        <div class="btn btn-submit" onclick="location.href='/vacancies/add'">Add</div>
        <div class="btn btn-reset">Back</div>
    </div>
    <br/>
<br/>
    <div class="geo-map" id="map"></div>
</div>
</body>
</html>
