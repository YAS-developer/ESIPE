<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Erreur Technique</title>
    <style>body { font-family: sans-serif; padding: 20px; color: #333; }</style>
</head>
<body>
    <h2 style="color:red">Une erreur est survenue</h2>
    <p>Le serveur a rencontré un problème inattendu.</p>
    
    <div style="background-color: #fce4e4; border: 1px solid red; padding: 10px;">
        <b>Détail :</b> <%= exception.getMessage() %>
    </div>
    
    <br/>
    <a href="${pageContext.request.contextPath}/JOperations.jsp">Réessayer</a>
</body>
</html>