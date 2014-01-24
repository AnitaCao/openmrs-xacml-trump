<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Policies" otherwise="/login.htm" redirect="/admin/users/policy.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><openmrs:message code="Policy.manage.title"/></h2>	

<spring:hasBindErrors name="policy">
	<openmrs:message code="fix.error"/>
	<div class="error">
		<c:forEach items="${errors.allErrors}" var="error">
			<openmrs:message code="${error.code}" text="${error.code}"/><br/><!-- ${error} -->
		</c:forEach>
	</div>
</spring:hasBindErrors>

<form method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<th><openmrs:message code="Policy.name"/></th>
			<td>
				<spring:bind path="policy.policy">
					<input type="text" name="name">
				</spring:bind>
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="general.description"/></th>
			<td valign="top">
					<spring:bind path="policy.description">
						<textarea name="description" rows="3" cols="50"></textarea>
					</spring:bind>
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="Policy.content"/></th>
			<td>
				<input type="file" name="content" />
			</td>
		</tr>
	</table>
	<input type="submit" value="<openmrs:message code="Policy.save"/>">
</form>	