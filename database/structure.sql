CREATE DATABASE artizana;
\c artizana;

CREATE TABLE Utilisateur(
    id_utilisateur SERIAL PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(40),
    mot_de_passe VARCHAR(255),
    etat INTEGER
);

CREATE TABLE Categorie(
    id_categorie SERIAL PRIMARY KEY,
    intitule VARCHAR(255),
    etat INTEGER
);

CREATE TABLE Societe(
    id_societe SERIAL PRIMARY KEY,
    id_utilisateur INTEGER,
    nom VARCHAR(255),
    titre VARCHAR(255),
    histoire TEXT,
    photo BYTEA,
    video BYTEA,
    etat INTEGER,
    FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);


CREATE TABLE Produit(
    id_produit SERIAL PRIMARY KEY,
    id_categorie INTEGER,
    id_societe INTEGER,
    intitule VARCHAR(255),
    etat INTEGER,
    FOREIGN KEY (id_categorie) REFERENCES Categorie(id_categorie),
    FOREIGN KEY (id_societe) REFERENCES Societe(id_societe)
);

CREATE TABLE Prix_produit(
    id_prix SERIAL PRIMARY KEY,
    id_produit INTEGER,
    prix DOUBLE PRECISION,
    "date" TIMESTAMP,
    FOREIGN KEY(id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE Photo_produit(
    id_photo SERIAL PRIMARY KEY,
    id_produit INTEGER,
    photo BYTEA,
    FOREIGN KEY(id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE Mouvement_stock(
    id_mouvement SERIAL PRIMARY KEY,
    id_produit INTEGER,
    quantite_entree INTEGER,
    quantite_sortie INTEGER,
    "date" TIMESTAMP,
    FOREIGN KEY(id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE Portefeuille(
    id_portefeuille SERIAL PRIMARY KEY,
    id_utilisateur INTEGER,
    monnaie DOUBLE PRECISION,
    FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);

CREATE TABLE Facture(
    id_facture SERIAL PRIMARY KEY,
    id_utilisateur INTEGER,
    "date" DATE,
    total DOUBLE PRECISION,
    etat INTEGER,
    FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);
ALTER TABLE Facture ADD COLUMN id_societe INTEGER REFERENCES Societe(id_societe);


CREATE TABLE Details_facture(
    id_details SERIAL PRIMARY KEY,
    id_facture INTEGER,
    id_produit INTEGER,
    quantite INTEGER,
    FOREIGN KEY(id_facture) REFERENCES Facture(id_facture),
    FOREIGN KEY(id_produit) REFERENCES Produit(id_produit)
);

CREATE TABLE Avis_client(
    id_avis SERIAL PRIMARY KEY,
    id_utilisateur INTEGER,
    id_societe INTEGER,
    avis TEXT,
    FOREIGN KEY(id_utilisateur) REFERENCES Utilisateur(id_utilisateur),
    FOREIGN KEY(id_societe) REFERENCES Societe(id_societe)
);


-- ALTER TABLE prix_produit
-- ALTER COLUMN date TYPE TIMESTAMP,
-- ALTER COLUMN date SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE produit
ADD COLUMN etat_validation INTEGER DEFAULT 0;
