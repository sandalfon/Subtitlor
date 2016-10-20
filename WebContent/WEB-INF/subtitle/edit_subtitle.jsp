<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<c:out value="aaa ${ finish }" />
	<br>
	<form method="post">
		<input type="submit" style="position: fixed; top: 10px; right: 10px;"
			name="save" /> <label
			style="position: fixed; top: 40px; right: 10px;"> Etat du
			travail</label><br>
			 <input type="radio" name="finish" value="yes"
			checked style="position: fixed; top: 60px; right: 40px;"><label
			style="position: fixed; top: 60px; right: 10px;">Oui</label><br>
		<input type="radio" name="finish" value="no"
			style="position: fixed; top: 80px; right: 40px;"><label
			style="position: fixed; top: 80px; right: 10px;">Non</label><br>
		<table>
			<c:forEach items="${ subtitles }" var="line" varStatus="status">
				<tr>
					<td style="text-align: right;"><c:out value="${ line }" /></td>
					<td><input type="text" name="line${ status.index }"
						id="line${ status.index }" size="35" /></td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>