<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Kvadrati brojeva</title>
</head>
<body>
	<h1>Hello World!</h1>
	<%
		Integer uspjeh = (Integer) request.getAttribute("uspjeh");
		if (uspjeh == 2) {
	%>

	Dragi korisniče, predao si nevaljani parametar.

	<%
		} else if (uspjeh == 3) {
	%>

	Kvadrat od broja
	<%=request.getAttribute("predaniBroj")%>
	iznosi
	<%=request.getAttribute("rezultat")%>.

	<%
		}
	%>

	<%
		if (uspjeh == 2) {
	%>

	Dragi korisniče, predao si nevaljani parametar.

	<%
		} else if (uspjeh == 3) {
	%>

	Kvadrat od broja
	<c:out value="${predaniBroj}" />
	iznosi
	<c:out value="${rezultat}" />
	<%
		}
	%>

	<c:choose>
		<c:when test="${uspjeh==2}">
	Dragi korisniče, predao si nevaljani parametar.</c:when>
		<c:when test="${uspjeh == 3 }">
	Kvadrat od broja
	<c:out value="${predaniBroj}" />
	iznosi
	<c:out value="${rezultat}" />

		</c:when>

	</c:choose>
</body>
</html>