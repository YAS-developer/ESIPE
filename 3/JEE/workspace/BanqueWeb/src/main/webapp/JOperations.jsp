<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Banque - Gestion (JSTL)</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .error { color: red; font-weight: bold; }
        .box { border: 1px solid #ccc; padding: 15px; margin-top: 15px; background-color: #f9f9f9; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #eee; }
    </style>
</head>
<body>
    <h2>Gestion des opérations bancaires</h2>

    <c:if test="${not empty msgErreur}">
        <p class="error">Erreur : ${msgErreur}</p>
    </c:if>

    <form action="GestionOperations" method="POST">
        <label>N° de compte : </label>
        <input type="text" name="NoDeCompte" value="${monBean.noDeCompte}" />
        <input type="submit" name="action" value="Consulter" />

        <c:if test="${not empty monBean and not empty monBean.nom}">
            
            <div class="box">
                <h3>Compte : ${monBean.nom} ${monBean.prenom}</h3>
                <p><b>Solde actuel : ${monBean.solde} €</b></p>

                <hr/>
                <label>Montant : </label>
                <input type="text" name="valeur" size="10"/>
                <input type="radio" name="op" value="+" checked /> Crédit
                <input type="radio" name="op" value="-" /> Débit
                <input type="submit" name="action" value="Traiter" />
            </div>

            <div class="box">
                <h3>Historique des opérations</h3>
                <label>Du : </label>
                <input type="text" name="dateInf" value="${not empty monBean.dateInf ? monBean.dateInf : '2004-01-01'}" size="10" placeholder="AAAA-MM-JJ"/>
                <label>Au : </label>
                <input type="text" name="dateSup" value="${not empty monBean.dateSup ? monBean.dateSup : '2025-12-31'}" size="10" placeholder="AAAA-MM-JJ"/>
                
                <input type="submit" name="action" value="Lister" />

                <c:if test="${not empty monBean.operationsParDates}">
                    <table>
                        <tr>
                            <th>Date</th>
                            <th>Heure</th>
                            <th>Opération</th>
                            <th>Valeur</th>
                        </tr>
                        <c:forEach var="ligne" items="${monBean.operationsParDates}">
                            <tr>
                                <td>${ligne[0]}</td>
                                <td>${ligne[1]}</td>
                                <td>${ligne[2]}</td>
                                <td>${ligne[3]} €</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                
                <c:if test="${param.action == 'Lister' and empty monBean.operationsParDates}">
                     <p><i>Aucune opération trouvée.</i></p>
                </c:if>
            </div>
        </c:if>
    </form>
    <div style="text-align:right; margin-bottom:10px;">
        <form action="GestionOperations" method="POST">
            <input type="submit" name="action" value="Deconnexion" />
        </form>
    </div>
</body>
</html>