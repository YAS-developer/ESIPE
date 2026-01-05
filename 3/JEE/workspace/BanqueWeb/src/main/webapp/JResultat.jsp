<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javaBeans.BOperations" %>

<!DOCTYPE html>
<html>
<head>
    <title>Banque Web - Résultat</title>
</head>
<body>
    <h2>Résultat de la consultation</h2>

    <%
        // 1. Récupération des données envoyées par le Servlet
        // On utilise le même nom que dans le servlet : "monBean" et "msgErreur"
        BOperations ops = (BOperations) request.getAttribute("monBean");
        String erreur = (String) request.getAttribute("msgErreur");

        // 2. Affichage conditionnel
        if (erreur != null) {
    %>
        <h3 style="color:red">Erreur : <%= erreur %></h3>
    <%
        } else if (ops != null) {
    %>
        <ul>
            <li><b>Compte :</b> <%= ops.getNoDeCompte() %></li>
            <li><b>Nom :</b> <%= ops.getNom() %></li>
            <li><b>Prénom :</b> <%= ops.getPrenom() %></li>
            <li><b>Solde :</b> <%= ops.getSolde() %> €</li>
        </ul>
    <%
        }
    %>
</body>
</html>