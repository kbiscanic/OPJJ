<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<body>
	<c:if test="${sessionScope.id!=null }">
		<c:out value="${sessionScope.fn }" />
		<c:out value="${sessionScope.ln }" /> (<a href="../logout">logout</a>)
</c:if>
	<c:if test="${sessionScope.id==null }">
		Not logged in.
</c:if>

	<h2>Blog entries:</h2>
	<ul>
		<c:forEach var="entry" items="${entries}">
			<li><a
				href="<c:out value="${nick}"/>/<c:out value="${entry.id}"/>">
					<c:out value="${entry.title}" />
			</a></li>
		</c:forEach>
	</ul>


	<c:if test="${sessionScope.nick == nick }">
		<a href="<c:out value="${nick }"/>/new">Add new blog entry</a>
		<br>
		<br>

	</c:if>

	<a href="../main">Back to home page!</a>

</body>
</html>