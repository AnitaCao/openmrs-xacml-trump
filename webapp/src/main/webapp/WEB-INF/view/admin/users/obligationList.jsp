<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/role.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2>Obligation Management</h2>
<a href="obligation.form">Add Obligation</a>
<br/><br/>

<b class="boxHeader"><openmrs:message code="ObligationContext.list.title"/></b>
<c:choose>
	<c:when test="${ not empty obligationList }">
		<table>
			<tr>
				<th> <openmrs:message code="general.name"/> </th>
				<th> <openmrs:message code="ObligationContext.name"/> </th>
			</tr>
			
			<c:forEach var="obligation" items="${obligationList}">
				<tr>
					<td>
						<c:out value="${obligation.obligationName}"/>
					</td>
					<td><c:out value="${obligation.contextName}"/></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<openmrs:message code="general.none" />
	</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp" %>