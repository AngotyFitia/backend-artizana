CREATE OR REPLACE VIEW v_produits_societe AS (
    SELECT p.*
        FROM Societe s JOIN Produit p ON s.id_societe=p.id_societe
);

CREATE OR REPLACE VIEW v_photos_produit AS (
    SELECT p.*, pp.id_photo, pp.photo
        FROM Produit p JOIN Photo_produit pp ON p.id_produit=pp.id_produit
);


