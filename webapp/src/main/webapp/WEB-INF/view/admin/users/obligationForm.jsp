<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/obligationcontext.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2>Obligation Management</h2>	

<form method="post">
	<table>
		<tr>
			<th><openmrs:message code="general.name"/></th>
			<td>
				<input type="text" name="obligationName">
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="ObligationContext.name"/></th>
			<td valign="top">
				<input type="text" name="contextName">
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="general.description"/></th>
			<td valign="top">
				<textarea name="description" rows="3" cols="50"></textarea>
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="Role.role"/></th>
			<td>
				<select name="role">
					<option></option>
					<openmrs:forEachRecord name="role">
						<c:if test="${record.role != 'Anonymous' && record.role != 'Authenticated'}">
							<option <c:if test="${param.role == record.role}">selected</c:if>><c:out value="${record.role}"/></option>
						</c:if>
					</openmrs:forEachRecord>
				</select>
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="ObligationContext.value"/></th>
			<td valign="top">
				<input type="text" name="value">
			</td>
		</tr>
	</table>
	<input type="submit" value="<openmrs:message code="ObligationContext.save"/>">
</form>
