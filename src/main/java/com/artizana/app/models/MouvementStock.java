package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class MouvementStock {

    int idMouvementStock;
    Produit produit;
    int quantiteEntree;
    int quantiteSortie;
    int quantiteActuel;
    Timestamp date;

    public int getIdMouvementStock() {
        return idMouvementStock;
    }
    public Produit getProduit() {
        return produit;
    }
    public int getQuantiteEntree() {
        return quantiteEntree;
    }
    public int getQuantiteSortie() {
        return quantiteSortie;
    }
    public Timestamp getDate() {
        return date;
    }
    public int getQuantiteActuel(){
        return quantiteActuel;
    }

    public void setIdMouvementStock(int idMouvementStock) throws Exception{
        this.idMouvementStock = idMouvementStock;
    }
    public void setProduit(Produit produit) throws Exception{
        this.produit = produit;
    }
    public void setQuantiteEntree(int quantiteEntree) throws Exception{
        this.quantiteEntree = quantiteEntree;
    }
    public void setQuantiteSortie(int quantiteSortie) throws Exception{
        this.quantiteSortie = quantiteSortie;
    }
    public void setDate(Timestamp date) throws Exception{
        this.date = date;
    }
    public void setQuantiteActuel(int quantiteActuel) throws Exception{
        this.quantiteActuel = quantiteActuel;
    }

    public MouvementStock() throws Exception{}
    public MouvementStock(int idMouvementStock, Produit produit, int quantiteEntree, int quantiteSortie, Timestamp date) throws Exception{
        setIdMouvementStock(idMouvementStock);
        setQuantiteEntree(quantiteEntree);
        setQuantiteSortie(quantiteSortie);
        setDate(date);
    }

    public boolean insert(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        boolean ok = false;
        try {
            if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
            }
            if (this.getQuantiteSortie() > 0) {
                Produit produit = this.getProduit().getById(this.getProduit().getIdProduit(), null);
                produit.getEtatStock(null);
                int stockActuel = produit.getEtatStock().getQuantiteActuel();
    
                if (this.getQuantiteSortie() > stockActuel) {
                    System.out.println(ok);
                    throw new Exception("Stock insuffisant : vous essayez d'acheter " + this.getQuantiteSortie() + 
                                        ", mais le stock disponible est " + stockActuel);
                }
            }
            String sql = "INSERT INTO mouvement_stock (id_produit, quantite_entree, quantite_sortie, date) VALUES (?, ?, ?, ?) RETURNING id_mouvement_stock";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.getProduit().getIdProduit());
            pstmt.setInt(2, this.getQuantiteEntree());
            pstmt.setInt(3, this.getQuantiteSortie());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
    
            res = pstmt.executeQuery();
            ok = true;
            if (res.next()) {
                this.setIdMouvementStock(res.getInt("id_mouvement_stock"));
            }
        } finally {
            if (res != null) res.close();
            if (pstmt != null) pstmt.close();
            if (!keepConnectionOpen) con.close();
        }
        return ok;
    }
    

}
