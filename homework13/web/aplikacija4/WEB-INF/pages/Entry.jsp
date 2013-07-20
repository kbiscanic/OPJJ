<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>
<body>
	<c:if test="${sessionScope.id!=null }">
		<c:out value="${sessionScope.fn }" />
		<c:out value="${sessionScope.ln }" /> (<a
			href="/aplikacija4/servleti/logout">logout</a>)
</c:if>
	<c:if test="${sessionScope.id==null }">
		Not logged in.
</c:if>
	<br>
	<br>
	
	<c:if test="${error!=null}">
		<b><font color="RED"><c:out value="${error }" /></font></b>
	</c:if>

	<form action="save" method="post">
		<input type="text" value="${entry.id }" hidden="true" name="id">
		Title:<br> <input type="text" value="${entry.title }"
			name="title"><br> Text: <br>
		<textarea rows="20" cols="80" name="text"><c:out value="${entry.text }"/></textarea>
		<br> <input type="submit" value="Save">
	</form>
	
	
	<a href="/aplikacija4">Back to home page!</a>

</body>
</html>