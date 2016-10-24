<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Les sous-titres</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
	
	<p><h1>Les sous-titres</h1></p>
	<p><h3>Un sous titre doit avoir un nom pour pouvoir être téléchargé</h3></p>
	<form method="post" action="subtitles">
	<table id="tableSubTitle" style="width: 100%">
		<tr>
			<th rowspan="2" style="text-align: left;">Nom de la video</th>
			<th rowspan="2" style="text-align: left;">Sous-titre Original</th>
			<th colspan="2"  style="text-align: left;">Anglais</th>
			<th colspan="2" style="text-align: left;">Français</th>
			<th colspan="2" style="text-align: left;">Allemand</th>
			<th colspan="2" style="text-align: left;">Espagnol</th>
			<th colspan="2" style="text-align: left;">Portugais</th>
		</tr>
		<tr><th style="text-align: left;">Nom</<th>
		<th style="text-align: left;">Etat</th>
		<th style="text-align: left;">Nom</th>
		<th style="text-align: left;">Etat</th>
		<th style="text-align: left;">Nom</th>
		<th style="text-align: left;">Etat</th>
		<th style="text-align: left;">Nom</th>
		<th style="text-align: left;">Etat</th>
		<th style="text-align: left;">Nom</th>
		<th style="text-align: left;">Etat</th>
		</tr>
		<c:forEach var="subtitleInfo" items="${subtitleInfos}">
			<tr>

				<td rowspan="2"><c:out value="${subtitleInfo.nameVideo}" /></td>
				<td rowspan="2"><c:choose>
						<c:when test="${subtitleInfo.vo == 'en' }">
							<c:out value="Anglais" />
						</c:when>
						<c:when test="${subtitleInfo.vo == 'fr' }">
							<c:out value="Anglais" />
						</c:when>
						<c:when test="${subtitleInfo.vo == 'en' }">
							<c:out value="Français" />
						</c:when>
						<c:when test="${subtitleInfo.vo == 'al' }">
							<c:out value="Allemand" />
						</c:when>
						<c:when test="${subtitleInfo.vo == 'es' }">
							<c:out value="Espagnol" />
						</c:when>
						<c:when test="${subtitleInfo.vo == 'pt' }">
							<c:out value="Portugais" />
						</c:when>
						<c:otherwise>
							<c:out value="Inconnu" />
						</c:otherwise>
					</c:choose></td>
				<td ><c:out
						value="${ empty subtitleInfo.nameEn ? 'Non défini' : subtitleInfo.nameEn }" />
				</td>
				<td><c:out
						value="${ subtitleInfo.finishedEn ? 'Fini' : 'En cours' }" />
				
				</td>
				<td ><c:out
						value="${ empty subtitleInfo.nameFr ? 'Non défini' : subtitleInfo.nameFr }" />
				</td>
				<td><c:out
						value="${ subtitleInfo.finishedFr ? 'Fini' : 'En cours' }" />
				
				</td>
				<td ><c:out
						value="${ empty subtitleInfo.nameAl ? 'Non défini' : subtitleInfo.nameAl }" />
				</td>
				<td><c:out
						value="${ subtitleInfo.finishedAl ? 'Fini' : 'En cours' }" />
				
				</td>
				<td ><c:out
						value="${ empty subtitleInfo.nameEs ? 'Non défini' : subtitleInfo.nameEs }" />
				</td>
				<td><c:out
						value="${ subtitleInfo.finishedEs ? 'Fini' : 'En cours' }" />
				
				</td>
				<td ><c:out
						value="${ empty subtitleInfo.namePt ? 'Non défini' : subtitleInfo.namePt }" />
				</td>
				<td><c:out
						value="${ subtitleInfo.finishedPt ? 'Fini' : 'En cours' }" />
				
				</td>
			</tr>
			<tr>
				<td>
					<button name="selectedButton" type="submit"
						value="edit_en_${ subtitleInfo.id}">edit</button>
				</td>

				<td><button name="selectedButton" type="submit"
						value="dl_en_${ subtitleInfo.id}"  ${ empty subtitleInfo.nameEn ? 'disabled' : ''} >Télécharger</button></td>
				<td>
					<button name="selectedButton" type="submit"
						value="edit_fr_${ subtitleInfo.id}">edit</button>
				</td>

				<td><button name="selectedButton" type="submit"
						value="dl_fr_${ subtitleInfo.id}" ${ empty subtitleInfo.nameFr ? 'disabled' : ''}>Télécharger</button></td>
				<td>
					<button name="selectedButton" type="submit"
						value="edit_al_${ subtitleInfo.id}">edit</button>
				</td>

				<td><button name="selectedButton" type="submit"
						value="dl_al_${ subtitleInfo.id}" ${ empty subtitleInfo.nameAl ? 'disabled' : ''}>Télécharger</button></td>
				<td>
					<button name="selectedButton" type="submit"
						value="edit_es_${ subtitleInfo.id}">edit</button>
				</td>

				<td><button name="selectedButton" type="submit"
						value="dl_es_${ subtitleInfo.id}" ${ empty subtitleInfo.nameEs ? 'disabled' : ''}>Télécharger</button></td>
				<td>
					<button name="selectedButton" type="submit"
						value="edit_pt_${ subtitleInfo.id}">edit</button>
				</td>

				<td><button name="selectedButton" type="submit"
						value="dl_pt_${ subtitleInfo.id}" ${ empty subtitleInfo.namePt ? 'disabled' : ''}>Télécharger</button></td>
			</tr>
		</c:forEach>

	</table>
	</form>
</body>
</html>