<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>

	<h1>Available polls:</h1>
	<c:if test="${polls.isEmpty() }">
		<a href="init">Click here to initialize database.</a>
	</c:if>
	<ol>
		<c:forEach var="entry" items="${polls}">
			<li><a href="glasanje?pollID=<c:out value="${entry.value}"/>">
					<c:out value="${entry.key}" />
			</a></li>
		</c:forEach>
	</ol>

</body>
</html>