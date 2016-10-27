<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajout d'un sous-titre original</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<h2>Formulaire d'ajout d'un nouveau sous-titre à partir d'un fichier</h2>
	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>

		<form method="post" action="addoriginal" enctype="multipart/form-data" id="addSub">
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
				name="language" value="En" checked> <label class="radioBut">Anglais</label><br>
			<input type="radio" name="language" value="Fr"> <label class="radioBut">Français</label><br>
			<input type="radio" name="language" value="Al"> <label class="radioBut">Allemand</label><br>
			<input type="radio" name="language" value="Es"> <label class="radioBut">Espagnol</label><br>
			<input type="radio" name="language" value="Pt"> <label class="radioBut">Portugais</label><br>

		</p>
		<p>
			<label>Sous-titre complet ?</label> <br> <input type="radio"
				name="finish" value="yes" checked> <label class="radioBut">Oui</label> <input
				type="radio" name="finish" value="no"> <label class="radioBut">Non</label>
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