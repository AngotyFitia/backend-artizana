package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class PhotoProduit {
    int idPhoto;
    Produit produit;
    byte[] photo;

    public int getIdPhoto() {
        return idPhoto;
    }
    public Produit getProduit() {
        return produit;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setIdPhoto(int idPhoto) throws Exception{
        this.idPhoto = idPhoto;
    }
    public void setProduit(Produit produit) throws Exception{
        this.produit = produit;
    }
    public void setPhoto(byte[] photo) throws Exception{
        this.photo = photo;
    }

    public PhotoProduit() throws Exception{}
    public PhotoProduit(int idPohoto, Produit produit, byte[] photo) throws Exception{
        setIdPhoto(idPohoto);
        setProduit(produit);
        setPhoto(photo);
    }

    public PhotoProduit insert(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
                if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
        }
            String sql = "INSERT INTO photo_produit (id_produit, photo) VALUES (?, ?) RETURNING id_photo";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.getProduit().getIdProduit());
            pstmt.setBytes(2, this.getPhoto()); 

            res = pstmt.executeQuery();
            if (res.next()) {
                this.setIdPhoto(res.getInt("id_photo"));
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

    public static PhotoProduit[] getByProduit(Produit produit, Connection con) throws Exception {
        Vector<PhotoProduit> photos = new Vector<>();
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
            }

            String sql = "SELECT * FROM photo_produit WHERE id_produit = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, produit.getIdProduit());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idPhoto = rs.getInt("id_photo");
                byte[] photoBytes = rs.getBytes("photo");
                PhotoProduit pp = new PhotoProduit(idPhoto, produit, photoBytes);
                photos.add(pp);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (!keepConnectionOpen && con != null) con.close();
        }

        PhotoProduit[] result = new PhotoProduit[photos.size()];
        photos.toArray(result);
        return result;
    }

}
