-- Suppression des tables si elles existent déjà
DROP TABLE IF EXISTS Visionne CASCADE;
DROP TABLE IF EXISTS Attribue CASCADE;
DROP TABLE IF EXISTS Joue CASCADE;
DROP TABLE IF EXISTS Realise CASCADE;
DROP TABLE IF EXISTS Film_Episode CASCADE;
DROP TABLE IF EXISTS Serie CASCADE;
DROP TABLE IF EXISTS Profil CASCADE;
DROP TABLE IF EXISTS Client CASCADE;
DROP TABLE IF EXISTS Acteur_Realisateur CASCADE;
DROP TABLE IF EXISTS Label CASCADE;
DROP TABLE IF EXISTS Abonnement CASCADE;

-- Création des tables
CREATE TABLE Abonnement (
    type_ VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Client (
    idClient INT PRIMARY KEY,
    type_ VARCHAR(50),
    nom VARCHAR(100),
    prenom VARCHAR(100),
    courriel VARCHAR(100),
    mdp VARCHAR(100),
    adresse TEXT,
    dateFinAbonnement DATE,
    FOREIGN KEY (type_) REFERENCES Abonnement(type_)
);

CREATE TABLE Profil (
    idProfil INT PRIMARY KEY,
    idClient INT,
    nom VARCHAR(100),
    FOREIGN KEY (idClient) REFERENCES Client(idClient)
);


CREATE TABLE Acteur_Realisateur (
    idActeurRealisateur INT PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100)
);

CREATE TABLE Film_Episode (
    idFilmEpisode INT PRIMARY KEY,
    titre VARCHAR(255),
    realisateur INT,
    anneeProduction YEAR,
    duree INT,
    FOREIGN KEY (realisateur) REFERENCES Client(idActeurRealisateur)
);

CREATE TABLE Serie (
    idSerie INT PRIMARY KEY,
    titre VARCHAR(255),
    anneeProduction YEAR
);


CREATE TABLE Label (
    idLabel INT PRIMARY KEY,
    nom VARCHAR(100)
);

CREATE TABLE Attribue (
    idProfil INT,
    idLabel INT,
    idFilmEpisode INT,
    PRIMARY KEY (idProfil, idLabel, idFilmEpisode),
    FOREIGN KEY (idProfil) REFERENCES Profil(idProfil),
    FOREIGN KEY (idLabel) REFERENCES Label(idLabel),
    FOREIGN KEY (idFilmEpisode) REFERENCES Film_Episode(idFilmEpisode)
);

CREATE TABLE Visionne (
    idProfil INT,
    idFilmEpisode INT,
    temps TIME DEFAULT '00:00:00',
    note DECIMAL(3,1),
    PRIMARY KEY (idProfil, idFilmEpisode),
    FOREIGN KEY (idProfil) REFERENCES Profil(idProfil),
    FOREIGN KEY (idFilmEpisode) REFERENCES Film_Episode(idFilmEpisode)
);

CREATE TABLE Joue (
    idFilmEpisode INT,
    idActeurRealisateur INT,
    role VARCHAR(100),
    PRIMARY KEY (idFilmEpisode, idActeurRealisateur),
    FOREIGN KEY (idFilmEpisode) REFERENCES Film_Episode(idFilmEpisode),
    FOREIGN KEY (idActeurRealisateur) REFERENCES Acteur_Realisateur(idActeurRealisateur)
);


-- Insertion dans 'Abonnement'
INSERT INTO Abonnement (type_) VALUES ('Standard'), ('Premium');

-- Insertion dans 'Client'
INSERT INTO Client (idClient, type_, nom, prenom, courriel, mdp, adresse, dateFinAbonnement) VALUES
(1, 'Premium', 'Doe', 'John', 'john.doe@example.com', 'joseph1234', '123 Main St', '2024-12-31'),
(2, 'Standard', 'Smith', 'Jane', 'jane.smith@example.com', 'marie1234','456 Elm St' '2024-12-31');

-- Insertion dans 'Profil'
INSERT INTO Profil (idProfil, idClient, nom) VALUES
(1, 1, 'Max'),
(2, 1, 'Henri'),
(3, 1, 'Annabelle'),
(4, 1, 'Marine'),
(5, 2, 'Jose');

-- Insertion dans 'Film_Episode'
INSERT INTO Film_Episode (idFilmEpisode, titre, realisateur, anneeProduction, duree) VALUES
(1, 'Espace Lointain', 3, 2022, 120),
(2, 'Aventure Fantastique', 3, 2023, 90);

-- Insertion dans 'Serie'
INSERT INTO Serie (idSerie, titre, anneeProduction) VALUES
(1, 'Les Mystères de l Univers', 2022),
(2, 'Quête Épique', 2023);

-- Insertion dans 'Acteur_Realisateur'
INSERT INTO Acteur_Realisateur (idActeurRealisateur, nom, prenom) VALUES
(1, 'Hello', 'Leonardo'),
(2, 'Martice', 'Lewin')
(3, 'Bratpite', 'Papite');

-- Insertion dans 'Label'
INSERT INTO Label (idLabel, nom) VALUES
(1, 'Science-fiction'),
(2, 'Fantasy');

-- Insertion dans 'Attribue'
INSERT INTO Attribue (idProfil, idLabel, idFilmEpisode) VALUES
(1, 1, 1),
(2, 2, 2);

-- Insertion dans 'Visionne'
INSERT INTO Visionne (idProfil, idFilmEpisode, temps, note) VALUES
(1, 1, '01:30:00', 8.5),
(2, 2, '00:45:00', 9.0);

-- Insertion dans 'Joue'
INSERT INTO Joue (idFilmEpisode, idActeurRealisateur, role) VALUES
(1, 1, 'La reine galactique'),
(2, 2, 'Elastique Man');



-- Affiche la liste de profil de l’utilisateur 1.
SELECT * FROM Profil WHERE idClient = 1;

-- Affiche le nom du profil choisi de: MAX (idProfil = 2)
SELECT nom FROM Profil WHERE idProfil = 2;

-- Affiche la liste des films et épisodes avec son label favori de MAX (idProfil = 2)
SELECT Film_Episode.titre FROM Film_Episode JOIN Attribue ON Film_Episode.idFilmEpisode = Attribue.idFilmEpisode JOIN Label ON Attribue.idLabel = Label.idLabel WHERE Attribue.idProfil = 2;

-- Affiche les notes du film de Max (idProfil = 2)
SELECT Film_Episode.titre, Visionne.note FROM Visionne JOIN Film_Episode ON Visionne.idFilmEpisode = Film_Episode.idFilmEpisode WHERE Visionne.idProfil = 2;

-- Reprends à partir de là où il s’est arrêté dans un de ces films de Max (idProfil = 2)
SELECT Film_Episode.titre, Visionne.temps FROM Visionne JOIN Film_Episode ON Visionne.idFilmEpisode = Film_Episode.idFilmEpisode WHERE Visionne.idProfil = 2;

-- Afficher les acteurs du film avec idFilmEpisode = 1
SELECT Acteur_Realisateur.nom, Acteur_Realisateur.prenom, Joue.role FROM Joue JOIN Acteur_Realisateur ON Joue.idActeurRealisateur = Acteur_Realisateur.idActeurRealisateur WHERE Joue.idFilmEpisode = 1;

-- Afficher la note moyenne d'un film spécifique (idFilmEpisode)
SELECT idFilmEpisode, AVG(note) AS MoyenneNote FROM Visionne WHERE idFilmEpisode = [idFilmEpisode] GROUP BY idFilmEpisode;

-- Mettre à jour une note pour le profil Max (idProfil = 1, idFilmEpisode = 1)
UPDATE Visionne SET note = 9.0 WHERE idProfil = 1 AND idFilmEpisode = 1;
