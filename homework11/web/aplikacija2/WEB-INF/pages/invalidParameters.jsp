<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>

<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>
	<p>Some of the parameters were invalid or missing.</p>

	<p>
		<a href="index.jsp">Back to home page</a>
	</p>
</body>
</html>