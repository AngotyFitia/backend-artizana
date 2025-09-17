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

CREATE OR REPLACE VIEW v_stock_produit AS(
    SELECT 
        p.id_produit,
        p.intitule,
        (SUM(ms.quantite_entree) - SUM(ms.quantite_sortie)) AS stock_actuel
    FROM produit p
    LEFT JOIN mouvement_stock ms ON p.id_produit = ms.id_produit
    GROUP BY p.id_produit, p.intitule
    ORDER BY p.id_produit
);

CREATE OR REPLACE VIEW v_produit AS (
    SELECT * FROM produit WHERE etat_validation = 10
);

CREATE OR REPLACE VIEW v_produit_avec_stock AS (
    SELECT p.*, CASE 
                    WHEN v.stock_actuel IS NULL THEN 0 
                    ELSE v.stock_actuel
                END stock
    FROM v_produit p 
    JOIN v_stock_produit v on p.id_produit = v.id_produit
);

