<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<body>
	<c:if test="${sessionScope.id!=null }">
		<c:out value="${sessionScope.fn }" />
		<c:out value="${sessionScope.ln }" /> (<a href="logout">logout</a>)
</c:if>
	<c:if test="${sessionScope.id==null }">
		Not logged in.
</c:if>


	<c:if test="${sessionScope.id==null }">
		<h3>Log in:</h3>

		<c:if test="${error!=null}">
			<b><font color="RED"><c:out value="${error }" /></font></b>
		</c:if>
		<form name="login" method="post">
			Nick: <input type="text" name="nick" value=<c:out value="${nick}"/>><br>
			Password: <input type="password" name="password"><br> <input
				type="submit" value="Log in"> <input type="submit"
				formaction="register" value="Register">
		</form>
	</c:if>

	<h2>Registered authors:</h2>
	<ul>
		<c:forEach var="user" items="${users}">
			<li><a href="author/<c:out value="${user.nick}"/>"> <c:out
						value="${user.firstName}" /> <c:out value="${user.lastName }"></c:out>
			</a></li>
		</c:forEach>
	</ul>

</body>
</html>