-- 	Entrainement au TP noté - ce travail ne sera PAS évalué.
--
--	Consigne originale
-----------------------
--  Utilisez le script magasin_exam.sql afin de créer la base de données qui sera utilisée pour les questions suivantes.
--  Prenez le temps de vous familiariser avec les différentes tables et leur contenu avant de commencer à répondre aux questions.
--  Pour chaque question, donnez la requête SQL permettant d'obtenir le résultat demandé, et ajoutez en commentaire le nombre de lignes renvoyées par cette requête.
--  Soignez la présentation (indentation, nom des variables, etc.) de vos requêtes. La lisibilité de votre travail sera prise en compte.
--  A la fin du TP, déposez votre fichier dans la zone de rendu prévue à cet effet sur elearning.
--  Pensez à enregistrer régulièrement votre travail afin de ne pas le perdre en cas de panne.
--
--
--1. La liste de tous les clients, avec tous leurs attributs.
SELECT * FROM client;
-- 200 rows


--2. La liste des villes où il y a un magasin, sans doublons.
SELECT DISTINCT ville FROM magasin;
-- 20 rows


--3. La liste des numéros de clients qui habitent à Paris ou à Lyon.
SELECT numcli FROM client WHERE ville='paris' OR ville='lyon';
--16 rows


--4. La liste des identifiants de produits dont un magasin a plus de 1000 exemplaires en stock.
SELECT idpro FROM stocke WHERE quantite > 1000;
--419 rows


--5. La liste des cartes de fidélité (numcarte) qui ont été créées en 2017.
SELECT numcarte FROM fidelite WHERE datecreation BETWEEN '2017-01-01' AND '2017-12-31';
--18 rows

--6. La liste des clients (numcli, nom, prenom) qui ont acheté un fauteuil.
SELECT numcli, nom, prenom FROM client NATURAL JOIN facture NATURAL JOIN contient NATURAL JOIN produit p WHERE p.libelle='fauteuil';
--64 rows


--7. Les numéros et noms des clients qui ont fait un achat dans un magasin qui n'est pas dans la ville où ils habitent.
SELECT DISTINCT(c.numcli), c.nom FROM client c JOIN facture f ON c.numcli=f.numcli JOIN magasin m ON f.idmag=m.idmag WHERE c.ville <> m.ville;
--200 rows

--8. Les identifiants des q dont on connait la couleur.
SELECT idpro FROM produit WHERE libelle='téléphone' AND couleur IS NOT NULL;
--1 row

--9. Les libellés des produits qui existent à la fois en gris et en blanc.
SELECT * FROM produit WHERE couleur='gris' INTERSECT SELECT * FROM produit WHERE couleur='blanc';
--2 rows

--10. Les villes dans lesquelles il y a des magasins mais pas de clients.
SELECT ville FROM magasin WHERE ville NOT IN(
    SELECT ville FROM client
);
--1 row

--11. Le prix moyen et le prix le plus bas d'un bureau.
SELECT AVG(prixUnit) as prix_moyen, MIN(prixUnit) as prix_bas FROM stocke NATURAL JOIN produit WHERE libelle='bureau';
--1 row

--12. La liste des villes où l'on peut acheter un moniteur, avec le prix du moniteur le moins cher que l'on peut trouver dans cette ville.
SELECT ville, MIN(prixUnit) FROM magasin NATURAL JOIN stocke GROUP BY ville;  
--20 rows

--13. La liste des magasins (idmag, nom) qui ont édité au moins 20 factures, triée par nombre de factures décroissant.
SELECT m.idmag, m.nom, COUNT(*) AS nombre_de_factures
FROM magasin m
JOIN facture f ON m.idmag = f.idmag
GROUP BY m.idmag, m.nom
HAVING COUNT(*) >= 20
ORDER BY nombre_de_factures DESC;


--14. La liste des clients (numcli, prénom, nom) avec pour chacun l'argent total qu'il a dépensé.



--15. La liste des paires de clients différents (numcli1, prenom1, numcli2, prenom2) qui ont fait un achat le même jour dans le même magasin.



--16. Les clients (numcli, prenom, nom) qui ont assez d'argent sur une de leurs cartes de fidélité pour acheter le produit le plus cher du magasin d'où provient la carte.



--17. La liste des clients (numcli) avec pour chacun le montant de la facture la moins chère qu'il a payée.



--18. Le produit (idpro, libelle) dont l'écart entre les prix minimaux et maximaux est le plus grand.



--19. Les clients (numcli, prenom, nom) dont aucune facture ne coûte plus de 400 euros.



--20. Les magasins qui ont au moins un produit de chaque libellé en stock.



