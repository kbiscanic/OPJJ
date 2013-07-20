<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<body>
	<h1>Registration page:</h1>

	<c:if test="${error!=null}">
		<b><font color="RED"><c:out value="${error }" /></font></b>
	</c:if>
	<form name="register" method="post">
		First name: <input type="text" name="firstName"
			value=<c:out value="${firstName}"/>><br> Last name: <input
			type="text" name="lastName" value=<c:out value="${lastName}"/>><br>
		E-mail: <input type="text" name="email"
			value=<c:out value="${email}"/>><br> Nick: <input
			type="text" name="nick" value=<c:out value="${nick}"/>><br>
		Password: <input type="password" name="password"><br> <input
			type="submit" value="Register">
	</form>
	
	<a href="main">Back to home page!</a>

</body>
</html>