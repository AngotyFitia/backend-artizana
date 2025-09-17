package com.artizana.app.models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class Produit {

    int idProduit;
    Categorie categorie;
    Societe societe;
    String intitule;
    int etat;
    PhotoProduit[] photosProduit;
    PrixProduit prixProduit;
    MouvementStock etatStock;
    int etat_validation;

    public int getEtat_validation() {
        return etat_validation;
    }

    public void setEtat_validation(int etat_validation) {
        this.etat_validation = etat_validation;
    }

    public int getIdProduit() {
        return this.idProduit;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public Societe getSociete() {
        return this.societe;
    }

    public String getIntitule() {
        return this.intitule;
    }

    public int getEtat() {
        return this.etat;
    }

    public PhotoProduit[] getPhotosProduit() {
        return photosProduit;
    }

    public MouvementStock getEtatStock() {
        return etatStock;
    }

    public PrixProduit getPrixProduit() {
        return prixProduit;
    }

    public void setIdProduit(int idProduit) throws Exception {
        this.idProduit = idProduit;
    }

    public void setCategorie(Categorie categorie) throws Exception {
        this.categorie = categorie;
    }

    public void setSociete(Societe societe) throws Exception {
        this.societe = societe;
    }

    public void setIntitule(String intitule) throws Exception {
        if (intitule.length() == 0) {
            throw new Exception("intitule nulle.");
        }
        this.intitule = intitule;
    }

    public void setEtat(int etat) throws Exception {
        if (etat < 0) {
            throw new Exception("etat invalide: negatif");
        }
        this.etat = etat;
    }

    public void setEtat(String etat) throws Exception {
        int a = Integer.valueOf(etat);
        if (etat.length() == 0) {
            throw new Exception("etat invalide: null");
        }
        this.setEtat(a);
    }

    public void setPhotosProduit(PhotoProduit[] photosProduit) {
        this.photosProduit = photosProduit;
    }

    public void setPrixProduit(PrixProduit prixProduit) {
        this.prixProduit = prixProduit;
    }

    public Produit() throws Exception {
    }

    public Produit(int id, String intitule, Categorie categorie, Societe societe, int etat) throws Exception {
        this.setIdProduit(id);
        this.setIntitule(intitule);
        this.setEtat(etat);
        this.setCategorie(categorie);
        this.setSociete(societe);
    }

    public Produit insert(Connection con) throws Exception {
        boolean valid = true;
        Statement stmt = null;
        ResultSet res = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            stmt = con.createStatement();
            String sql = "INSERT INTO PRODUIT VALUES(DEFAULT," + this.getCategorie().getIdCategorie() + ","
                    + this.getSociete().getIdSociete() + ",'" + this.getIntitule() + "',  1 , 0) returning id_produit";
            System.out.println(sql);
            res = stmt.executeQuery(sql);
            if (res.next())
                this.setIdProduit(res.getInt("id_produit"));
            System.out.println(this.getIdProduit());
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (!valid) {
                con.close();
            }
        }
        return this;
    }

    public PhotoProduit[] getAllPhotos(Connection con) throws Exception {
        Vector<PhotoProduit> liste = new Vector<PhotoProduit>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_photos_produit WHERE id_produit=" + this.getIdProduit();
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                Produit produit = new Produit().getById(result.getInt("id_produit"), null);
                byte[] photo = result.getBytes("photo");
                PhotoProduit societe = new PhotoProduit(id, produit, photo);
                liste.add(societe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (result != null) {
                    result.close();
                }
                if (valid == false || con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        PhotoProduit[] photos = new PhotoProduit[liste.size()];
        liste.toArray(photos);
        this.setPhotosProduit(photos);
        return photos;
    }

    public PrixProduit getPrix(Connection con) throws Exception {
        PrixProduit prixProduit = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_prix_produit_recent WHERE id_produit=" + this.getIdProduit();
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id_prix");
                Produit produit = new Produit().getById(result.getInt("id_produit"), null);
                double prix = result.getDouble("prix");
                Timestamp date = result.getTimestamp("date");
                prixProduit = new PrixProduit(id, produit, prix, date);
                this.setPrixProduit(prixProduit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (result != null) {
                    result.close();
                }
                if (valid == false || con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prixProduit;
    }

    public MouvementStock getEtatStock(Connection con) throws Exception {
        MouvementStock mouvementStock = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_stock_produit WHERE id_produit=" + this.getIdProduit();
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                mouvementStock = new MouvementStock();
                Produit produit = new Produit().getById(result.getInt("id_produit"), null);
                mouvementStock.setProduit(produit);
                mouvementStock.setQuantiteActuel(result.getInt("stock_actuel"));
                this.setEtatStock(mouvementStock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (result != null) {
                    result.close();
                }
                if (valid == false || con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mouvementStock;
    }

    public Produit[] getAll(Connection con) throws Exception {
        Vector<Produit> liste = new Vector<Produit>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_produit_avec_stock WHERE etat=1 AND etat_validation = 10";
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id_produit");
                Categorie categorie = new Categorie().getById(result.getInt("id_categorie"), null);
                Societe societe = new Societe().getById(result.getInt("id_societe"), null);
                String intitule = result.getString("intitule");
                int etat = result.getInt("etat");
                Produit produit = new Produit(id, intitule, categorie, societe, etat);
                MouvementStock m = new MouvementStock();
                m.setQuantiteActuel(result.getInt("stock"));
                produit.setEtatStock(m);
                liste.add(produit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (result != null) {
                    result.close();
                }
                if (valid == false || con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Produit[] produits = new Produit[liste.size()];
        liste.toArray(produits);
        return produits;
    }

    public Produit getById(int idProduit, Connection con) throws Exception {
        Produit produit = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM produit WHERE id_produit=" + idProduit;
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                Categorie categorie = new Categorie().getById(result.getInt(2), null);
                Societe societe = new Societe().getById(result.getInt(3), null);
                String intitule = result.getString(4);
                int etat = result.getInt(5);
                produit = new Produit(id, intitule, categorie, societe, etat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
                if (result != null) {
                    result.close();
                }
                if (valid == false || con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return produit;
    }

    public void update(Connection con) throws Exception {
        boolean valid = true;
        Statement stmt = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            stmt = con.createStatement();
            String sql = "UPDATE produit SET intitule='" + this.getIntitule() + "' WHERE id_produit="
                    + this.getIdProduit();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (!valid) {
                con.close();
            }
        }
    }

    public void delete(Connection con) throws Exception {
        boolean valid = true;
        Statement stmt = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            stmt = con.createStatement();
            String sql = "UPDATE produit SET etat=10 WHERE id_produit=" + this.getIdProduit();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (!valid) {
                con.close();
            }
        }
    }

    public boolean validerProduit(int idProduit, Connection con) throws Exception {
        boolean valid = false;
        boolean result = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String sql = "UPDATE produit SET etat_validation = 10 WHERE id_produit = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idProduit);
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (valid)
                con.close();
        }

        return result;
    }

    public void setEtatStock(MouvementStock etatStock) {
        this.etatStock = etatStock;
    }

    public Produit[] getAllNonValid(Connection con) throws Exception {
        ArrayList<Produit> list_produit = new ArrayList<>();
        boolean valid = false;
        ResultSet rs = null;
        PreparedStatement prep = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String request = "SELECT * FROM produit WHERE etat_validation = ?";
            prep = con.prepareStatement(request);
            prep.setInt(1, 0);

            rs = prep.executeQuery();

            while (rs.next()) {
                int cat = rs.getInt("id_categorie");
                Categorie categorie = new Categorie().getById(cat, null);

                int s = rs.getInt("id_societe");
                Societe societe = new Societe().getById(s, null);

                int id = rs.getInt("id_produit");
                String intitule = rs.getString("intitule");
                Produit p = new Produit();
                p.setCategorie(categorie);
                p.setSociete(societe);
                p.setIntitule(intitule);
                p.setIdProduit(id);
                list_produit.add(p);

            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (prep != null)
                    prep.close();
                if (valid && con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(list_produit.size());
        return list_produit.toArray(new Produit[0]);
    }

}