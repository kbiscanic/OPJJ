<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>

<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>
	<table border="1">
		<thead>
			<tr>
				<th>Number</th>
				<th>Square</th>
			</tr>
		</thead>
		<c:forEach var="number" items="${squares}">
			<tr>
				<td><c:out value="${number.key}" /></td>
				<td><c:out value="${number.value}" /></td>
			</tr>
		</c:forEach>
	</table>
	<p>
		<a href="index.jsp">Back to home page</a>
	</p>
</body>
</html>