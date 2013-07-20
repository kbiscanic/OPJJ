<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${sessionScope.pickedBgCol==null}">
	<c:set var="pickedBgCol" value="WHITE" scope="session" />
</c:if>

<html>
<body bgcolor=<c:out value ="${sessionScope.pickedBgCol }"/>>
	<p>
		<font color=<%=new Random().nextInt()%>> U ovom slučaju narodna
			poslovica “Ako neće milom, onda će silom” ne vridi. Ova poslovica u
			informatici općenito ne vridi. U informatici vridi modificirana
			verzija ove poslovice koja glasi “Ako neće milom, onda neće nikako”.

			Međutim, glavni junak ove priče to nije zna. Nije zna iz nekoliko
			razloga. Prvi razlog je šta nije čita ovaj blog, a to nije ni moga
			jer bi triba čitat tekst u budućnost od nekoliko godina. Pa mu prvi
			razlog opraštamo. Drugi razlog je taj šta me nije sluša kad je bija
			kod mene. A nije me sluša jer je mislija da će kupnjom dijelova za PC
			tamo di je najjeftinije proć bolje i jeftinije, a kako je to usput
			sam sastavlja mislija je … ko zna šta je mislija? Davno je to bilo,
			uglavnom ovaj post bi izaša ranije, ali jedva san naša slike u nekim
			starim direktorijima, a nisan ga tija objavit bez slika. Ide priča…

			Doša prijo u butigu, zanima se za kupnju računala. U to vrime se
			prodava socket 478. Ništa, nako mi malo proćakulali, ja mu objasnija
			šta i kako i pošto, ali prijo nako neodlučan, sve mu nešto skupo, pa
			bil on to moga sam kupit jeftinije u Zagrebu ?!?, pa bil on moga
			kupit dijelove pa da on to sam sastavi, iman li ja šta polovnih
			dijelova upo cijene i tako neka spika. Uglavnom uzeja ponudu i oša
			ča. Odma san zna da neće kupit kod mene, nije mi baš bija kadar koji
			bi izdvojija 60-70 Eura za kvalitetno AOpen kućište koja san
			standardno u to vrime prodava. Prošlo desetak dana evo ti njega na
			vrata, nosi svoju kašetu ispod ruke. Kupija je negdi ko zna šta i sad
			ne radi. Ajmo vit šta je. Spojin ja to njegovo sranje i upalin. Upali
			se, ali ne daje sliku na monitor. Otvorin bočnu stranicu. Kuleri se
			vrtu. Vidin po kuleru od procesora da je P3 s370. Zaminin RAM. Ništa.
			Zaminin grafičku. Ništa. Izvadin modem. Ništa. Zaminin napajanje.
			Ništa. Odspojin floppy, disk i CD. Ništa. Odspojin tipkovnicu i miša.
			Ništa. Ciman kuler. Stoji čvrsto. Pitan ja “Dis ovo kupija?” – “A
			malo u jednog rođe, malo priko oglasa, malo u jednoj firmi.” – A ko
			je ovo sastavlja? – “Ja.” U pičku materinu, koji je kurac, zaminija
			san sve živo a ne radi. Mora da je maderbord. Pitan ga di je nabavija
			matičnu ploču, kaže da je kupija priko oglasa i da je ispravna.

			Ništa, jeben se ja tu još neko vrime, ali nema slike na monitoru. Kad
			najedanput prijo mene pita: “Čuj, jel ima kakve veze to šta kad san
			stavlja procesor u matičnu ploču, malo je teže ulazija?” – “Kako to
			misliš malo je teže ulazija, nema on šta teže ulazit, on lipo uđe bez
			problema.” Meni već alarm u glavi, jebate konj, da glupan nije
			okrenija za 90 ili 180 procesor, ma nije sigurno, na socketu lipo
			falu na 2 kantuna po 1 pin, nema šanse da fališ. Nema šanse da fališ?
			Ima, ima i te kako ima. Ovaj prijo ne samo da je zasra i šefa i
			stanicu, nego je omašio ceo fudbal. Kad san skinija kuler, nisan moga
			virovat svojin očima. Majstor je u socket 370 za Intelove procesore
			uredno ugura AMD. Uglavnom, uzeo sam svjetlopisni uređaj i uredno
			dokumentirao hardverski incident. Šlag dolazi uvik na kraju pa tako i
			u ovoj priči. Stavija san u taj maderbord neki Intel s370 procesor i
			PC je radija normalno. </font>
	</p>
	<p>
		<a href="http://informaticar.eu/042-ako-nece-milom/">Source</a>
	</p>
	<p>
		<a href="../index.jsp">Back to home page</a>
	</p>
</body>
</html>