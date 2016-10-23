<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Editer les sous-titres</title>
</head>
<body>
	<%@ include file="../menu.jsp"%>
	<p><c:out value="${videoName}"/></p>
	<form method="post" action="saveSubtitles">
	<p><input type="submit" style="position: fixed; top: 10px; right: 10px;" name="save" /> </p>
	<label 	style="position: fixed; top: 40px; right: 10px;"> Etat du travail</label><br>
	<p><input type="radio" name="finish" value="yes" 
			checked style="position: fixed; top: 60px; right: 40px;"><label
			style="position: fixed; top: 60px; right: 10px;">Oui</label><br>
		<input type="radio" name="finish" value="no"
			style="position: fixed; top: 80px; right: 40px;"><label
			style="position: fixed; top: 80px; right: 10px;">Non</label> </p>
	<table>
	<tr>
	<td>
	<c:out value="Nom de la version originale: ${nameRef }"/>
	</td>
	<td>  <c:out value="Nom de la traduction: "/>  <input type="text" name="nameTarget" value="${empty nameTarget ? 'non défini' : nameTarget }"/> 
	</td>
	</tr>
	<c:forEach items="${ ids }" var="id" varStatus="status">
	<tr>
	<td style="text-align: right;"><c:out value="${ subtitleRef[id] }"/></td>
	<td> <input type="text" name="subTarget_${id}" value="${empty subtitleTarget[id] ? '' : subtitleTarget[id] }"/>
	</tr>
	</c:forEach>
	</table>
	</form>
	
<%-- 	<c:out value="aaa ${ finish }" /> --%>
<!-- 	<br> -->
<!-- 	<form method="post"> -->
<!-- 		<input type="submit" style="position: fixed; top: 10px; right: 10px;" -->
<!-- 			name="save" /> <label -->
<!-- 			style="position: fixed; top: 40px; right: 10px;"> Etat du -->
<!-- 			travail</label><br> -->
<!-- 			 <input type="radio" name="finish" value="yes" -->
<!-- 			checked style="position: fixed; top: 60px; right: 40px;"><label -->
<!-- 			style="position: fixed; top: 60px; right: 10px;">Oui</label><br> -->
<!-- 		<input type="radio" name="finish" value="no" -->
<!-- 			style="position: fixed; top: 80px; right: 40px;"><label -->
<!-- 			style="position: fixed; top: 80px; right: 10px;">Non</label><br> -->
<!-- 		<table> -->
<%-- 			<c:forEach items="${ subtitles }" var="line" varStatus="status"> --%>
<!-- 				<tr> -->
<%-- 					<td style="text-align: right;"><c:out value="${ line }" /></td> --%>
<%-- 					<td><input type="text" name="line${ status.index }" --%>
<%-- 						id="line${ status.index }" size="35" /></td> --%>
<!-- 				</tr> -->
<%-- 			</c:forEach> --%>
<!-- 		</table> -->
<!-- 	</form> -->
</body>
</html>