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

	<h2>
		<c:out value="${entry.title }" />
	</h2>
	<p>
		<c:out value="${entry.text }"></c:out>
	</p>

	<c:if test="${sessionScope.nick == nick }">
		<a href="edit?id=${entry.id }">Edit blog entry</a>
	</c:if>

	<c:if test="${!entry.comments.isEmpty() }">
		<ul>

			<c:forEach var="comment" items="${entry.comments }">
				<li><div style="font-weight: bold">
						[<a href="mailto:${comment.usersEMail}"><c:out
								value="${comment.usersEMail}"/></a>] ${comment.postedOn }
					</div>
					<div style="padding-left: 10px;">${comment.message }</div></li>
			</c:forEach>

		</ul>

	</c:if>

	<h3>Add new comment:</h3>

	<c:if test="${error!=null}">
		<b><font color="RED"><c:out value="${error }" /></font></b>
	</c:if>
	<form name="comment" method="post">
		E-mail: <input type="text" name="email" value="${email }"><br>
		Message: <br>
		<textarea rows="5" cols="80" name="message"><c:out value="${message }"/></textarea>
		<br> <input type="submit" value="Post new comment">
	</form>


	<a href="/aplikacija4">Back to home page!</a>

</body>
</html>