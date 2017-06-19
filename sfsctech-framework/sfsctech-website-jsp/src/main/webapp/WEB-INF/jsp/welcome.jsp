<html>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<body>
	<div style="border: 1px red solid">
		helloJsp.html
		<br>
		<c:url value="/resources/text.txt" var="url"/>
		<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
		Spring URL: ${springUrl} at ${time}
		<br>
		JSTL URL: ${url}
		<br>
		Message: ${message}
		<br>
		Exception: <spring:message code='tips.exception.sys'/>
	</div>
</body>

</html>
