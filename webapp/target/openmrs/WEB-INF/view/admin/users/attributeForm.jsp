<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/attribute.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><openmrs:message code="Policy.manage.attributes"/></h2>	

<form method="post">
	<table>
		<tr>
			<th><openmrs:message code="Policy.attribute.name"/></th>
			<td>
				<input type="text" name="attrib_name">
			</td>
		</tr>
		<tr>
			<th><openmrs:message code="Policy.attribute.type"/></th>
			<td>
				<select name="attrib_type">
					<option></option>
  					<option value="1">Subject</option>
  					<option value="2">Action</option>
  					<option value="3">Resource</option>
				</select> 
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="Policy.attribute.data.type"/></th>
			<td valign="top">
				<select name="data_type">
					<option></option>
  					<option value="integer">Integer</option>
  					<option value="long">Long</option>
				</select> 
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
			<th valign="top"><openmrs:message code="Policy.attribute.change.stratergy"/></th>
			<td>
				<table>
					<tr>
						<td><input type="radio" name="change_policy" value="0" checked="checked"><openmrs:message code="Policy.attribute.dynamic"/></td>
						<td><input type="radio" name="change_policy" value="1"><openmrs:message code="Policy.attribute.static"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<th><openmrs:message code="Policy.attribute.intial.value"/></th>
			<td>
				<input type="text" name="initial_value">
			</td>
		</tr>
	</table>
	<input type="submit" value="<openmrs:message code="Policy.attribute.save"/>">
</form>	