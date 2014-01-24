<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/role.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2>Attribute Management</h2>
<a href="attribute.form">Add Attribute</a>

<b class="boxHeader">Existing Dynamic Attributes</b>
<form method="post" class="box">
<table>
		<tr>
			<th>Name</th>
			<th>Type</th>
			<th>Data Type</th>
			<th>Owner</th>
			<th>Initial Value</th>
		</tr>
		
		<c:forEach var="attribute" items="${attributeList}">
			<tr>
				<td align="center">
					<c:out value="${attribute.attributeName}"/>
				</td>
				<td align="center">
					<c:out value="${attribute.attributeType}"/>
				</td>
				<td align="center">
					<c:out value="${attribute.dataType}"/>
				</td>
				<td align="center">
					<c:out value="${attribute.ownerType}"/>
				</td>
				<td align="center">
					<c:out value="${attribute.initialValue}"/>
				</td>
			</tr>
		</c:forEach>
</table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>