<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><html>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>

	<h1>Rezultati glasanja:</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entry" items="${sessionScope.results}">
				<tr>
					<td><c:out value="${entry.name}" /></td>
					<td><c:out value="${entry.votes}" /></td>
				</tr>


			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>


	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="entry" items="${winners}">
			<li><a href="<c:out value="${entry.hyperlink}"/>"><c:out
						value="${entry.name}" /></a>
		</c:forEach>

	</ul>


	<p>
		<a href="index.jsp">Back to home page</a>
	</p>
</body>
</html>