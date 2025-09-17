package com.artizana.app.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DetailsFacture {

    int idDetails;
    Facture facture;
    Produit produit;
    int quantite;

    public int getIdDetails() {
        return idDetails;
    }
    public Facture getFacture() {
        return facture;
    }
    public Produit getProduit() {
        return produit;
    }
    public int getQuantite() {
        return quantite;
    }

    public void setIdDetails(int idDetails) throws Exception{
        this.idDetails = idDetails;
    }
    public void setFacture(Facture facture) throws Exception{
        this.facture = facture;
    }
    public void setProduit(Produit produit) throws Exception{
        this.produit = produit;
    }
    public void setQuantite(int quantite) throws Exception{
        this.quantite = quantite;
    }

    public DetailsFacture()throws Exception{}
    public DetailsFacture(int idDetails, Facture facture, Produit produit, int quantite)throws Exception{
        setIdDetails(idDetails);
        setFacture(facture);
        setProduit(produit);
        setQuantite(quantite);
    }

    public DetailsFacture insert(Connection con)throws Exception{
        boolean valid=true;
        Statement stmt =null;
        ResultSet res=null;
        try{
            if(con==null){
                con = Connect.connectDB();
                valid=false;
            } 
            stmt= con.createStatement();
            String sql="INSERT INTO Details_facture VALUES(DEFAULT,"+this.getFacture().getIdFacture()+","+this.getProduit().getIdProduit()+", "+this.getQuantite()+") returning id_details_facture";
            System.out.println(sql);
            res=stmt.executeQuery(sql);
            if(res.next()) this.setIdDetails(res.getInt("id_details_facture"));
            System.out.println(this.getIdDetails());
        }catch(Exception e){
            throw e;
        }finally{
            if(stmt!=null){ stmt.close(); }
            if(!valid){ con.close(); }
        }
        return this;
    }
}
