<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/privilege.list" />
<openmrs:message var="pageTitle" code="Privilege.manage.titlebar" scope="page"/>	
	
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><openmrs:message code="Policy.migration.title"/></h2>	

<form method="post" class="box">
		<b class="boxHeader">System User Information</b>
		<c:choose>
			<c:when test="${ not empty usersMap }">
				<table>
					<tr>
						<th>User Name</th>
						<th>Assigned Roles</th>
						<th>Virtual Role Name</th>
					</tr>
					
					<c:forEach var="map" items="${usersMap}">
						<tr>
							<td><c:out value="${map.key}"/></td>
							<td>
								<c:out value="${map.value.assignedRoles}"/>
							</td>
							<td>${map.value.virtualRole}</td>
						</tr>
					</c:forEach>
					<tr></tr>
					<tr></tr>
				</table>
			</c:when>
			<c:otherwise>
				<openmrs:message code="general.none" />
			</c:otherwise>
		</c:choose>
		
		<b class="boxHeader">Policy Generation Methodology</b>
		<table>
		<tr>
			<th>Policy Generation Methodology</th>
		</tr>
		<tr>
			<td><input type="radio" name="method" value="role" checked="checked">Role Based</td>
		</tr>
		<tr>
			<td><input type="radio" name="method" value="user">User Based</td>
		</tr>
		</table>
	<input type="submit" value="Generate Policy">
</form>	