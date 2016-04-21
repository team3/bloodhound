<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <td class="location">${e.address}</td>
      <td><a href="${pageContext.request.contextPath}../vacancies/delete/${e.id}">†</a></td>
    </tr>
  </c:forEach>
</table>