<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="//code.jquery.com/jquery-2.1.4.min.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Bilberry | Search page</title>
</head>
<body>

<jsp:include page="../tiles/header.jsp" flush="false"/>

<div class="body-content">
    <div class="search-info-header" style="width:100%">
        <div class="search-info-header-element search-info-header-profiles"
             style="float:left; width: auto; font-size: smaller">
            <b>${profiles_count}</b> profiles
        </div>
        <div class="search-info-header-element search-info-header-view-all"
             style="float: left; width: 20px; font-size: smaller">
            <a href="${pageContext.request.contextPath}/candidates/list"
               style="color: dodgerblue; font-size: smaller"> (all)</a>
        </div>
        <div class="search-info-header-element search-info-header-search-history"
             style="float: right; width: auto; font-size: smaller;">
            <a href="${pageContext.request.contextPath}/search/history">Search history</a>
        </div>
    </div>

    <form:form commandName="candidateSearchForm" action="candidates/search" method="POST" cssClass="common-form">
        <table style="width: 100%">
            <tr>
                <td colspan="2"><form:input type="text" alt="select language" id="candidate_skills" path="skills"
                                            style="width: 100%; font-size: 16px; height: 40px;" placeholder="skills"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <form:input path="stringLocation" cssStyle="display: none" id="candidate_location_position"/>
                    <input type="text" id="candidate_location" style="width: 100%; font-size: 16px; height: 40px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="btn btn-submit" onclick="submitSearchForm();">Submit</div>
                    <div class="btn btn-reset" onclick="resetSearchForm()">Reset</div>
                </td>
            </tr>
        </table>
    </form:form>

    <p style="${pageContext.request.contextPath}font-size: medium;"><a href="/vacancies/list"
                                                                      style="color: mediumvioletred">${vacancies_count}
        Vacancies</a></p>

    <div id = "skills_autocompleted"></div>
</div>

<jsp:include page="../tiles/footer.jsp" flush="false"/>

<script>
    function initAutocomplete() {
        var autocomplete = new google.maps.places.Autocomplete(document.getElementById('candidate_location'), {types: ['geocode']});
        autocomplete.addListener('place_changed', function () {
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                window.alert("Autocomplete's returned place contains no geometry");
                return;
            } else {
                console.log(place.geometry.location);
                $("#candidate_location_position").val(place.geometry.location.lat() + ',' + place.geometry.location.lng());
            }
        });
    }
</script>

<script>
    $(function () {
        function split(val) {
            return val.split(/,\s*/);
        }

        function extractLast(term) {
            return split(term).pop();
        }

        $("#candidate_skills")
            // don't navigate away from the field on tab when selecting an item
                .bind("keydown", function (event) {
                    if (event.keyCode === $.ui.keyCode.TAB &&
                            $(this).autocomplete("instance").menu.active) {
                        event.preventDefault();
                    }
                })
                .autocomplete({
                    appendTo : "#skills_autocompleted",
                    source: function (request, response) {
                        $.getJSON("/candidates/skills", {
                            term: extractLast(request.term)
                        }, response);
                    },
                    search: function () {
                        // custom minLength
                        var term = extractLast(this.value);
                        if (term.length < 2) {
                            return false;
                        }
                    },
                    focus: function () {
                        // prevent value inserted on focus
                        return false;
                    },
                    select: function (event, ui) {
                        var terms = split(this.value);
                        // remove the current input
                        terms.pop();
                        // add the selected item
                        terms.push(ui.item.value);
                        // add placeholder to get the comma-and-space at the end
                        terms.push("");
                        this.value = terms.join(", ");
                        return false;
                    }
                });
    });

    function submitSearchForm() {
        $("#candidateSearchForm")[0].submit();
    }

    function resetSearchForm() {
        $("#candidateSearchForm")[0].reset();
    }
</script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBX8ViL1KgtaaAWXauAFkaQgyYxHcxFno0&signed_in=true&libraries=places&callback=initAutocomplete"
        async defer></script>

</body>
</html>