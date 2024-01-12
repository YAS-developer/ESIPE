

CREATE TABLE abonnement(
    type varchar(25) primary key
)

CREATE TABLE client(
    id_client int primary key,
    nom varchar(25),
    prenom varchar(25),
    adresse varchar(25),
    courriel varchar(25),
    type varchar(25) REFERENCES abonnement(type),
    data_fin_abonnement DATE
    UNIQUE(id_client)
);


CREATE TABLE profil(
    id_profil int primary key,
    nom varchar(25) REFERENCES client(nom),
);



CREATE TABLE serie(
    id_serie int primary key,
    titre varchar(20),
    anneeProduction 
)





