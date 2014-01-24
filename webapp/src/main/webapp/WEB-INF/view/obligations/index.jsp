<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:message var="pageTitle" code="Obligations.titlebar" scope="page"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<style>
	.adminMenuList #menu li {
		display: list-item;
		border-left-width: 0px;
		
	}
	.adminMenuList #menu li.first {
		display: none;
	}
	.adminMenuList #menu {
		list-style: none;
		margin-left: 10px;
		margin-top: 0;
	}
	h4 {
		margin-bottom: 0;
	}
</style>

<h2><openmrs:message code="Obligations.title"/></h2>

<%@ include file="/WEB-INF/template/footer.jsp" %>
