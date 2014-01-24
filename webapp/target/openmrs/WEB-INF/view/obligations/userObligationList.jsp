<%@ include file="/WEB-INF/template/include.jsp" %>

<%@ include file="/WEB-INF/template/header.jsp" %>

<h2><openmrs:message code="UserObligation.management"/></h2>

<form method="post">
	<table>
		<tr>
			<th><openmrs:message code="general.name"/></th>
			<td>
				<select name="obligationName">
					<option></option>
					<c:forEach items="${obligationList}" var="obligation">
                            <option value="${obligation.obligationName}">${obligation.obligationName}</option>
                    </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th valign="top"><openmrs:message code="general.value"/></th>
			<td valign="top">
				<input type="text" name="value">
			</td>
		</tr>
	</table>
	
	<input type="submit" value="<openmrs:message code="UserObligation.save"/>">
	<br>
	<br>
	
	<b class="boxHeader"><openmrs:message code="UserObligation.list.title"/></b>
	<c:choose>
		<c:when test="${ not empty userObligationList }">
			<table>
				<tr>
					<th> <openmrs:message code="general.name"/> </th>
					<th> <openmrs:message code="general.value"/> </th>
				</tr>
				
				<c:forEach var="userObligation" items="${userObligationList}">
					<tr>
						<td>
							<c:out value="${userObligation.obligation.obligationName}"/>
						</td>
						<td><c:out value="${userObligation.value}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
	<c:otherwise>
		<openmrs:message code="general.none" />
	</c:otherwise>
</c:choose>
	
</form>


<%@ include file="/WEB-INF/template/footer.jsp" %>