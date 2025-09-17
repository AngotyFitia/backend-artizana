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

CREATE OR REPLACE VIEW v_commission AS (
    SELECT *
    FROM commission 
    WHERE date_fin is not NULL 
);

CREATE OR REPLACE VIEW v_facture_non_validee AS (
    SELECT *
    FROM facture f 
    WHERE f.etat = 0 
);

CREATE OR REPLACE VIEW v_facture_by_day AS (
    SELECT f.date , COUNT(*) nombre 
    FROM v_facture_non_validee f 
    GROUP BY f.date
);

CREATE OR REPLACE VIEW v_facture_by_month AS (
    SELECT 
    EXTRACT(YEAR FROM f.date) AS annee,
    EXTRACT(MONTH FROM f.date) AS mois,
    COUNT(*) AS nb_factures
    FROM v_facture_non_validee f 
    GROUP BY EXTRACT(YEAR FROM f.date), EXTRACT(MONTH FROM f.date)
    ORDER BY annee, mois
);

CREATE OR REPLACE VIEW v_facture_by_year AS (
    SELECT EXTRACT(YEAR FROM f.date) AS annee , COUNT(*) AS nb_factures
    FROM v_facture_non_validee f
    GROUP BY EXTRACT(YEAR FROM f.date)
);

CREATE OR REPLACE VIEW v_vola_miditra_day AS (
    SELECT v.date , SUM(v.vola) vola 
    FROM vola_miditra v 
    GROUP BY v.date
);

CREATE OR REPLACE VIEW v_vola_miditra_month AS (
    SELECT 
        EXTRACT(YEAR FROM f.date) AS annee,
        EXTRACT(MONTH FROM f.date) AS mois,
        SUM(f.vola) vola
    FROM vola_miditra f 
    GROUP BY EXTRACT(YEAR FROM f.date), EXTRACT(MONTH FROM f.date)
    ORDER BY annee, mois
);

CREATE OR REPLACE VIEW v_vola_miditra_year AS (
    SELECT 
        EXTRACT(YEAR FROM f.date) AS annee,
        SUM(f.vola) vola
    FROM vola_miditra f 
    GROUP BY EXTRACT(YEAR FROM f.date)
);

CREATE OR REPLACE VIEW v_produit_mieux_vendu AS 
SELECT 
    p.intitule, 
    SUM(df.quantite) AS total_vendu
FROM Details_facture df
JOIN produit p ON p.id_produit = df.id_produit
GROUP BY p.intitule
ORDER BY total_vendu DESC;


CREATE OR REPLACE VIEW v_produit_vendu_week AS (
    SELECT 
        p.intitule,
        EXTRACT(YEAR FROM f.date) AS annee,
        EXTRACT(WEEK FROM f.date) AS semaine,
        SUM(df.quantite) AS total_vendu
    FROM Details_facture df
    JOIN facture f ON f.id_facture = df.id_facture
    JOIN produit p ON p.id_produit = df.id_produit
    GROUP BY p.intitule, EXTRACT(YEAR FROM f.date), EXTRACT(WEEK FROM f.date)
    ORDER BY annee, semaine, p.intitule
);

CREATE OR REPLACE VIEW v_produit_vendu_month AS (
    SELECT 
        p.intitule,
        EXTRACT(YEAR FROM f.date) AS annee,
        EXTRACT(MONTH FROM f.date) AS mois,
        SUM(df.quantite) AS total_vendu
    FROM Details_facture df
    JOIN facture f ON f.id_facture = df.id_facture
    JOIN produit p ON p.id_produit = df.id_produit
    GROUP BY p.intitule, EXTRACT(YEAR FROM f.date), EXTRACT(MONTH FROM f.date)
    ORDER BY annee, mois, p.intitule
);



