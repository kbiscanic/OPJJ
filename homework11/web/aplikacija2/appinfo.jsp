<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>

<html>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>
	<%
		Date runningTime = new Date(System.currentTimeMillis()
				- (Long) request.getServletContext().getAttribute(
						"startedTime"));
	%>
	<p>
		This web application has been running for:
		<%=runningTime.getDate() - 1%>
		days,
		<%=runningTime.getHours() - 1%>
		hours,
		<%=runningTime.getMinutes()%>
		minutes,
		<%=runningTime.getSeconds()%>
		seconds and
		<%=runningTime.getTime() % 1000%>
		milliseconds.
	</p>
	<p>
		<a href="index.jsp">Back to home page</a>
	</p>
</body>
</html>