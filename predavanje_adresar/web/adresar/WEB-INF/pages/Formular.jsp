<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Kontakt</title>
<style type="text/css">
.greska {
	font-family: fantasy;
	font-size: 0.9em;
	font-weight: bold;
	color: #ff0000;
}
</style>
</head>
<body>
	<h1>
		<c:choose>
			<c:when test="${zapis.id.isEmpty()}">Novi kontakt</c:when>
			<c:otherwise>Uređivanje kontakta</c:otherwise>
		</c:choose>
	</h1>

	<form action="save" method="post">

		<input type="hidden" name="id" value=<c:out value="${zapis.id }"/>
			size="5"><br>


		<!--
		ID <input type="text" name="id"
			value='<c:out value="${zapis.id }"></c:out>' size="5"><br>
		<c:if test="${zapis.imaGresku('id')}">
			<div class="greska">
				<c:out value="${zapis.dohvatiPogresku('id')}" />
			</div>
		</c:if>
		-->
		Ime <input type="text" name="ime"
			value='<c:out value="${zapis.ime }"></c:out>' size="20"><br>
		<c:if test="${zapis.imaGresku('ime')}">
			<div class="greska">
				<c:out value="${zapis.dohvatiPogresku('ime')}" />
			</div>
		</c:if>

		Prezime <input type="text" name="prezime"
			value='<c:out value="${zapis.prezime }"></c:out>' size="30"><br>
		<c:if test="${zapis.imaGresku('prezime')}">
			<div class="greska">
				<c:out value="${zapis.dohvatiPogresku('prezime')}" />
			</div>
		</c:if>

		E-mail <input type="text" name="email"
			value='<c:out value="${zapis.email }"></c:out>' size="50"><br>
		<c:if test="${zapis.imaGresku('email')}">
			<div class="greska">
				<c:out value="${zapis.dohvatiPogresku('email')}" />
			</div>
		</c:if>

		<input type="submit" name="metoda" value="Pohrani"> <input
			type="submit" name="metoda" value="Odustani">

	</form>
</body>

</html>