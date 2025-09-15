CREATE OR REPLACE VIEW v_produits_societe AS (
    SELECT p.*
        FROM Societe s JOIN Produit p ON s.id_societe=p.id_societe
);

CREATE OR REPLACE VIEW v_photos_produit AS (
    SELECT p.*, pp.id_photo, pp.photo
        FROM Produit p JOIN Photo_produit pp ON p.id_produit=pp.id_produit
);

CREATE OR REPLACE VIEW v_prix_produit_recent AS(
    SELECT DISTINCT ON (pp.id_produit) pp.id_produit, pp.id_prix, pp.prix, pp.date
    FROM Prix_produit pp JOIN Produit p ON pp.id_produit = p.id_produit
    ORDER BY pp.id_produit, pp.date DESC
);

CREATE OR REPLACE VIEW v_total_facture AS(
    SELECT f.id_facture, f.id_utilisateur, f.id_societe, f.date, f.etat,
        SUM(vp.prix * df.quantite) AS total_calcule
    FROM Facture f JOIN Details_facture df ON f.id_facture = df.id_facture
        JOIN Produit p ON df.id_produit = p.id_produit
        JOIN v_prix_produit_recent vp ON p.id_produit = vp.id_produit
    GROUP BY f.id_facture, f.id_utilisateur, f.id_societe, f.date, f.etat
);


