<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/role.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><openmrs:message code="Policy.manage.title"/></h2>

<a href="policy.form"><openmrs:message code="Policy.add"/></a>
<a href="policyMig.list"><openmrs:message code="Policy.migration"/></a>
<a href="attribute.list">Attribute Management</a>
<a href="obligation.list">Context Management</a>
<br/><br/>

<b class="boxHeader"><openmrs:message code="Policy.list.title"/></b>

<form method="post" class="box">
	<table>
		<tr>
			<th> </th>
			<th> <openmrs:message code="Policy.name"/> </th>
			<th> <openmrs:message code="general.description"/> </th>
		</tr>
		
		<c:forEach var="map" items="${policyList}">
			<tr>
				<td><input type="checkbox" name="policyId" value="<c:out value="${map.key}"/>"></td>
				<td>
					<!-- <a href="policy.form?policy=<c:out value="${map.key}"/>"> -->
						<c:out value="${map.key}"/>
					<!-- </a> -->
				</td>
				<td><c:out value="${map.value}"/></td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="<openmrs:message code="Policy.delete"/>">
</form>


<%@ include file="/WEB-INF/template/footer.jsp" %>