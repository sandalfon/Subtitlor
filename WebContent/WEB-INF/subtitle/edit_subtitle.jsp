<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<p>
	<h1>
		<c:out value="Nom de la vidéo : ${videoName}" />
	</h1>
	</p>
	<form method="post" action="saved">
		<p>
			<input type="submit" style="position: fixed; top: 10px; right: 10px;"
				name="save" />
		</p>
		<h2><label style="position: fixed; top: 40px; right: 10px;"> Etat
			du travail</label></h2><br>
		<p>
			<input type="radio" name="finish" value="yes" checked
				style="position: fixed; top: 60px; right: 70px;"><label
				style="position: fixed; top: 60px; right: 10px;">Fini</label><br>
			<input type="radio" name="finish" value="no"
				style="position: fixed; top: 80px; right: 70px;"><label
				style="position: fixed; top: 80px; right: 10px;">En cours</label>
		</p>
		<table>
			<tr>
				<td>
					<h2>
						<c:out value="Nom de la version originale: ${nameRef }" />
					</h2>
					<h3>
						<c:out value="Tag langue : ${languageRef }" />
					</h3>
				</td>
				<td><h2><c:out value="Nom de la traduction: " /> <input type="text"
					name="nameTarget"
					value="${empty nameTarget ? 'non défini' : nameTarget }" />
					</h2>
					<h3>
						<c:out value="Tag langue : ${languageTarget }" />
					</h3></td>
			</tr>
			<c:forEach items="${ ids }" var="id" varStatus="status">
				<tr>
					<td style="text-align: right;"><c:out
							value="${ subtitleRef[id] }" /></td>
<%-- 					<td><input type="text" name="subTarget_${id}" --%>
<%-- 						value="${empty subtitleTarget[id] ? '' : subtitleTarget[id] }" /> --%>
<!-- 						</td> -->
						<td>
						<textarea rows="2" cols="30" name="subTarget_${id}" >
${empty subtitleTarget[id] ? '' : subtitleTarget[id] }</textarea></td>
				</tr>
			</c:forEach>
		</table>
	</form>

</body>
</html>