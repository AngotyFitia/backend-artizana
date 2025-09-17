CREATE OR REPLACE FUNCTION insert_commande(
    p_id_utilisateur INT,
    p_id_societe INT,
    p_id_produit INT,
    p_quantite INT,
    p_prix INT
)
RETURNS INT AS $$
DECLARE
    v_id_facture INT;
    v_total INT;
BEGIN
    -- Étape 1 : Chercher une facture ouverte existante
    SELECT id_facture INTO v_id_facture
    FROM facture
    WHERE id_utilisateur = p_id_utilisateur
      AND id_societe = p_id_societe
      AND etat = 1
    ORDER BY id_facture DESC
    LIMIT 1;

    -- Étape 2 : Si pas trouvée, créer une nouvelle facture
    IF v_id_facture IS NULL THEN
        INSERT INTO facture(id_utilisateur, id_societe, date, total, etat)
        VALUES (p_id_utilisateur, p_id_societe, CURRENT_DATE, 0, 1)
        RETURNING id_facture INTO v_id_facture;
    END IF;

    -- Étape 3 : Insérer dans details_facture
    INSERT INTO details_facture(id_facture, id_produit, quantite)
    VALUES (v_id_facture, p_id_produit, p_quantite);

    -- Étape 4 : Recalcul du total (somme des quantités * prix unitaire)
    SELECT SUM(df.quantite * p_prix)
    INTO v_total
    FROM details_facture df
    WHERE df.id_facture = v_id_facture;

    -- Étape 5 : Mise à jour du total
    UPDATE facture
    SET total = v_total
    WHERE id_facture = v_id_facture;

    RETURN v_id_facture;
END;
$$ LANGUAGE plpgsql;


