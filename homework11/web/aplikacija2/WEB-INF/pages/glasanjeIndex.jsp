<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>

	<h1>Glasanje za omiljeni band:</h1>
	<p>Od slijedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach var="entry" items="${entryList}">
			<li><a href="glasanje-glasaj?id=<c:out value="${entry.id}"/>">
					<c:out value="${entry.name}" />
			</a></li>
		</c:forEach>
	</ol>
	<p>
		<a href="index.jsp">Back to home page</a>
	</p>

</body>
</html>