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
--1. La liste de tous les produits avec tous leurs attributs.

SELECT *
FROM produit;
-- 100 lignes


--2. La liste des villes où il y a un magasin, sans doublons.

SELECT DISTINCT ville
FROM magasin;
-- 20 lignes



--3. La liste des numéros de clients qui habitent à Paris ou à Lyon.
SELECT numcli
FROM client
WHERE ville = 'Paris' OR ville = 'lyon';
-- 7 lignes



--4. La liste des identifiants de produits dont un magasin a plus de 1000 exemplaires en stock.
SELECT idpro
FROM stocke
WHERE quantite > 1000;
-- 419 lignes



--5. La liste des cartes de fidélité (numcarte) qui ont été créées en 2017.
SELECT numcarte
FROM fidelite
WHERE datecreation >= '01-01-2017' AND datecreation <= '31-12-2017';
-- 18 lignes



--6. La liste des clients (numcli, nom, prenom) qui ont acheté un fauteuil.
SELECT numcli, nom, prenom
FROM client
NATURAL JOIN facture 
NATURAL JOIN contient
NATURAL JOIN produit
WHERE libelle='fauteuil';
-- 64 lignes


--7. Les numéros et noms des clients qui ont fait un achat dans un magasin qui n'est pas dans la ville où ils habitent.
SELECT distinct c.numcli, c.nom 
FROM client c
JOIN facture f on f.numcli = c.numcli
JOIN magasin m on m.idmag = f.idmag
WHERE m.ville != c.ville;
-- 200 lignes 



--8. Les identifiants des téléphones dont on connait la couleur.
SELECT idpro
FROM produit
WHERE libelle = 'téléphone' AND couleur IS NOT NULL;
-- 1 lignes


--9. Les noms de magasins que l'on trouve à la fois à Toulouse et à Lille.

SELECT m1.nom
FROM magasin m1
JOIN magasin m2 ON m1.nom = m2.nom AND m1.ville <> m2.ville
WHERE m1.ville = 'Toulouse' AND m2.ville = 'Lille';



--10. Les villes dans lesquelles il y a des magasins mais pas de clients.
SELECT DISTINCT ville
FROM magasin
EXCEPT
SELECT DISTINCT ville
FROM client;
-- 1 ligne




--11. Le prix le plus bas et le prix le plus haut d'un tableau.
SELECT min(prixUnit), max(prixUnit)
FROM stocke
NATURAL JOIN produit
WHERE libelle = 'tableau';
-- min : 77.47 | max : 137.45



--12. La liste des villes où il y a au moins un magasin, avec le nombre de magasins qui s'y trouvent.
SELECT ville, count(ville)
FROM magasin
Group by ville;
-- 20 lignes


--13. La liste des magasins (idmag, nom) qui ont édité au moins 20 factures, triée par nombre de factures décroissant.
SELECT idmag, nom 
FROM magasin
NATURAL JOIN facture
GROUP BY idmag
HAVING count(idfac) > 20;
-- 21 lignes


--14. La liste des clients (numcli, prénom, nom) avec pour chacun l'argent total qu'il a dépensé.
SELECT numcli, prenom, nom, sum(prixUnit * quantite)
FROM client
NATURAL JOIN facture
NATURAL JOIN contient
GROUP BY numcli;
-- 200 lignes


--15. La liste des paires de clients différents (numcli1, prenom1, numcli2, prenom2) qui ont fait un achat le même jour dans le même magasin.

SELECT f1.numcli AS numcli1, c1.prenom AS prenom1, f2.numcli AS numcli2, c2.prenom AS prenom2
FROM facture f1
JOIN facture f2 ON f1.idmag = f2.idmag AND f1.date = f2.date AND f1.numcli <> f2.numcli
JOIN client c1 ON f1.numcli = c1.numcli
JOIN client c2 ON f2.numcli = c2.numcli;



--16. Les clients (numcli, prenom, nom) qui ont assez d'argent sur une de leurs cartes de fidélité pour acheter le produit le plus cher du magasin d'où provient la carte.

SELECT numcli, prenom, nom, points
FROM client 
NATURAL JOIN fidelite f
WHERE points >= (
    SELECT max(prixUnit) 
    FROM stocke s
    WHERE s.idmag = f.idmag
);
-- 51 lignes



--17. La liste des clients (numcli) avec pour chacun le montant de la facture la moins chère qu'il a payée.

SELECT numcli, min(prixUnit * quantite)
FROM facture
NATURAL JOIN contient
GROUP BY numcli;
-- 200 lignes



--18. Le produit (idpro, libelle) dont l'écart entre les prix minimaux et maximaux est le plus grand.

SELECT idpro, libelle 
FROM produit
NATURAL JOIN stocke
GROUP BY idpro
ORDER BY max(prixUnit) - min(prixUnit) DESC
LIMIT 1;


--19. Les clients (numcli, prenom, nom) dont toutes les factures s'élèvent à plus de 750 euros.

-- SELECT numcli, prenom, nom
-- FROM client
-- NATURAL join facture
-- WHERE numcli NOT IN (
--     SELECT numcli, prixUnit*quantite
--     FROM facture
--     NATURAL JOIN contient
--     WHERE (prixUnit * quantite) < 750;
--     );


--     SELECT f.numcli
--     FROM facture f
--     NATURAL JOIN contient c
--     GROUP BY f.numcli, c.idfac
--     HAVING (c.prixUnit * c.quantite) > 750;

SELECT c.numcli, c.prenom, c.nom
FROM client c
WHERE NOT EXISTS (
    SELECT *
    FROM facture f
    JOIN contient co ON f.idfac = co.idfac
    WHERE f.numcli = c.numcli
    GROUP BY f.idfac
    HAVING SUM(co.prixUnit * co.quantite) <= 750
);

--20. Les magasins qui ont au moins un produit de chaque libellé en stock.



