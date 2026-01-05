<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page introuvable</title>
    <style>body { font-family: sans-serif; text-align: center; padding-top: 50px; color: #555; }</style>
</head>
<body>
    <h1 style="color:orange">Oups ! 404</h1>
    <h3>La page que vous cherchez n'existe pas.</h3>
    <p>Vérifiez l'URL ou retournez à l'accueil.</p>
    <a href="${pageContext.request.contextPath}/JOperations.jsp">Retour à la gestion bancaire</a>
</body>
</html>