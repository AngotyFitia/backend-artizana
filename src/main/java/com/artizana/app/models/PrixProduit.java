package com.artizana.app.models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrixProduit {
    int idPrix;
    Produit produit;
    double prix;
    Timestamp date;
    
    public int getIdPrix() {
        return idPrix;
    }
    public Produit getProduit() {
        return produit;
    }
    public double getPrix() {
        return prix;
    }
    public Timestamp getDate(){
        return date;
    }

    public void setIdPrix(int idPrix) throws Exception{
        this.idPrix = idPrix;
    }
    public void setProduit(Produit produit) throws Exception{
        this.produit = produit;
    }
    public void setPrix(double prix) throws Exception{
        this.prix = prix;
    }
    public void setDate(Timestamp date)throws Exception{
        this.date=date;
    }


    public PrixProduit() throws Exception{}
    public PrixProduit(int idPohoto, Produit produit, double prix, Timestamp date) throws Exception{
        setIdPrix(idPohoto);
        setProduit(produit);
        setPrix(prix);
        setDate(date);
    }

    public PrixProduit insert(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
                if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
        }
            String sql = "INSERT INTO prix_produit (id_produit, prix, date) VALUES (?, ?, ?) RETURNING id_prix";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.getProduit().getIdProduit());
            pstmt.setDouble(2, this.getPrix()); 
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            res = pstmt.executeQuery();
            if (res.next()) {
                this.setIdPrix(res.getInt("id_prix"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (res != null) res.close();
            if (pstmt != null) pstmt.close();
            if (!keepConnectionOpen) con.close();
        }
        return this;
    }

}
