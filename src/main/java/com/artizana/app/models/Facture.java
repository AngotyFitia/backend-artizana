package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Facture {

    int idFacture;
    Utilisateur utilisateur;
    Societe societe;
    Timestamp date;
    int total;
    int etat;
    DetailsFacture[] details;

    public int getIdFacture() {
        return idFacture;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public Societe getSociete() {
        return societe;
    }
    public Timestamp getDate() {
        return date;
    }
    public int getTotal() {
        return total;
    }
    public int getEtat() {
        return etat;
    }
    public DetailsFacture[] getDetails() {
        return details;
    }

    public void setIdFacture(int idFacture) throws Exception{
        this.idFacture = idFacture;
    }
    public void setUtilisateur(Utilisateur utilisateur) throws Exception{
        this.utilisateur = utilisateur;
    }
    public void setSociete(Societe societe) throws Exception{
        this.societe = societe;
    }
    public void setDate(Timestamp date) throws Exception{
        this.date = date;
    }
    public void setTotal(int total) throws Exception{
        this.total = total;
    }
    public void setEtat(int etat) throws Exception{
        this.etat = etat;
    }
    public void setDetails(DetailsFacture[] details) throws Exception{
        this.details = details;
    }

    public Facture() throws Exception{}
    public Facture(int idFacture, Utilisateur utilisateur, Societe societe, Timestamp date, int total, int etat) throws Exception{
        setIdFacture(idFacture);
        setUtilisateur(utilisateur);
        setSociete(societe);
        setDate(date);
        setTotal(total);
        setEtat(etat);
    }

    public Facture insert(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
                if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
        }
            String sql = "INSERT INTO facture (id_utilisateur, id_societe, date, total, etat) VALUES (?, ?, ?, ?, ?) RETURNING id_facture";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.getUtilisateur().getIdUtilisateur());
            pstmt.setInt(2, this.getSociete().getIdSociete());
            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            pstmt.setInt(4, this.getTotal()); 
            pstmt.setInt(5, this.getEtat()); 
            res = pstmt.executeQuery();
            if (res.next()) {
                this.setIdFacture(res.getInt("id_facture"));
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


    public static int insertCommande( int idUtilisateur, int idSociete, int idProduit, int quantite, int prixProduit, Connection con ) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idFacture = -1;
        boolean keepConnectionOpen = (con != null);
        try {
            if (con == null) {
                con = Connect.connectDB();
            }

            String sql = "SELECT insert_commande(?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idSociete);
            stmt.setInt(3, idProduit);
            stmt.setInt(4, quantite);
            stmt.setInt(5, prixProduit);
            rs = stmt.executeQuery();
            if (rs.next()) {
                idFacture = rs.getInt(1);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (!keepConnectionOpen && con != null) con.close();
        }
        return idFacture;
    }

}
