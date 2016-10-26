<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajout d'un titre original</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
	<form method="post" action="addoriginal" enctype="multipart/form-data">
		<p>
			<label for="name">Nom du sous titre : </label> <input type="text"
				name="name" id="name" />
		</p>
		<p>
			<label for="nameVideo">Nom de la video : </label> <input type="text"
				name="nameVideo" id="nameVideo" />
		</p>
		<p>

			<label>Langue du fichier</label> <br> <input type="radio"
				name="language" value="En" checked> <label>Anglais</label><br>
			<input type="radio" name="language" value="Fr"> <label>Français</label><br>
			<input type="radio" name="language" value="Al"> <label>Allemand</label><br>
			<input type="radio" name="language" value="Es"> <label>Espagnol</label><br>
			<input type="radio" name="language" value="Pt"> <label>Portugais</label><br>

		</p>
		<p>
			<label>Sous-titre complet ?</label> <br> <input type="radio"
				name="finish" value="yes" checked> <label>Oui</label> <input
				type="radio" name="finish" value="no"> <label>Non</label>
		</p>
		<p>
		<p>
			<label for="file">Fichier à envoyer : </label> <input type="file"
				name="file" id="file" accept=".srt" />
		</p>
		</p>
		<input type="submit" />
	</form>
</body>
</html>