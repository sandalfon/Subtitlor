<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Accueil</title>

    </head>
    <body>
        <%@ include file="menu.jsp" %>
        <p><h1>Bienvenu sur le projet OC JAVA EE sous-titre !</h1></p>
        <p><h2>Merci de bien vouloir adapter les informations de connections dans : com.subtitlor.dao.DaoFactory </h2></p>
        <p><h2>Merci de bien vouloir changer les chemins dans com.subtitlor.utilities.FileHandler.java : FILE_PATH et TMP_PATH</h2></p>
        <p><h2>Merci de bien vouloir changer le chemin dans WebContent.WEB-INF.web.xml : location </h2></p>
        <p><h2>Dans le dossier DB/sql_back : le backup de la base sql</h2></p>
        <p><h2>Dans le dossier DB/workbench : le schema de la base de donnée</h2></p>
        <p><h2>Dans le dossier WebContent/WEB-INF/data : des sous-titres </h2> attention le sous-titres SILICON.VALLEY ne sont pas en utf-8, fichier de test pour les caractères \r\n</p>
    </body>
</html>