<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>

<html>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>
	<p>
		<a href="colors.jsp">Background color chooser</a>
	</p>
	<p>
		<a href="squares?a=100&b=120"> Squares </a>
	</p>
	<p>
		<a href="stories/funny.jsp"> Funny story </a>
	</p>
	<p>
		<a href="reports.jsp">OS usage</a>
	</p>
	<p>
		<a href="powers?a=1&b=100&n=3"> Powers </a>
	</p>
	<p>
		<a href="appinfo.jsp">Application info</a>
	</p>
	<p>
		<a href="glasanje"> Glasanje</a>
	</p>
</body>
</html>