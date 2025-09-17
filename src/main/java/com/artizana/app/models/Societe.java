package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.Base64;

public class Societe {

    int idSociete;
    Utilisateur utilisateur;
    String nom;
    String titre;
    String histoire;
    byte[] photo;
    byte[] video;
    int etat;
    String photoBase64;
    String videoBase64;
    Produit[] produits;

    public int getIdSociete() {
        return this.idSociete;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public String getNom() {
        return this.nom;
    }

    public String getTitre() {
        return this.titre;
    }

    public String getHistoire() {
        return this.histoire;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public byte[] getVideo() {
        return this.video;
    }

    public int getEtat() {
        return this.etat;
    }

    public Produit[] getProduits() {
        return produits;
    }

    public void setIdSociete(int idSociete) throws Exception {
        this.idSociete = idSociete;
    }

    public void setUtilisateur(Utilisateur utilisateur) throws Exception {
        this.utilisateur = utilisateur;
    }

    public void setNom(String nom) throws Exception {
        if (nom.length() == 0) {
            throw new Exception("Le nom de la société est invalide.");
        }
        this.nom = nom;
    }

    public void setTitre(String titre) throws Exception {
        if (titre.length() == 0) {
            throw new Exception("Le titre de la société est invalide.");
        }
        this.titre = titre;
    }

    public void setHistoire(String histoire) throws Exception {
        if (histoire.length() == 0) {
            throw new Exception("L'histoire de la société est invalide.");
        }
        this.histoire = histoire;
    }

    public void setPhoto(byte[] photo) throws Exception {
        if (photo == null) {
            throw new Exception("Veuillez insérez une photo de la société.");
        }
        this.photo = photo;
    }

    public void setVideo(byte[] video) throws Exception {
        if (video == null) {
            throw new Exception("Veuillez insérez une video de la société.");
        }
        this.video = video;
    }

    public void setEtat(int etat) throws Exception {
        this.etat = etat;
    }

    public void setPhotoBase64(String base64) {
        this.photoBase64 = base64;
        if (base64 != null && !base64.isEmpty()) {
            this.photo = Base64.getDecoder().decode(base64);
        }
    }

    public void setVideoBase64(String base64) {
        this.videoBase64 = base64;
        if (base64 != null && !base64.isEmpty()) {
            this.video = Base64.getDecoder().decode(base64);
        }
    }

    public void setProduits(Produit[] produits) throws Exception {
        this.produits = produits;
    }

    public Societe() throws Exception {
    }

    public Societe(int id, String nom, Utilisateur utilisateur, String titre, String histoire, byte[] photo,
            byte[] video, int etat) throws Exception {
        setIdSociete(id);
        setNom(nom);
        setUtilisateur(utilisateur);
        setHistoire(histoire);
        setTitre(titre);
        setPhoto(photo);
        setVideo(video);
        setEtat(etat);
    }

    public Societe(int id) throws Exception {
        setIdSociete(id);
    }

    public Societe insert(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
            }
            String sql = "INSERT INTO societe ( id_utilisateur,  nom, titre, histoire,   photo,  video,etat) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_societe";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.getUtilisateur().getIdUtilisateur());
            pstmt.setString(2, this.getNom());
            pstmt.setString(3, this.getTitre());
            pstmt.setString(4, this.getHistoire());
            pstmt.setBytes(5, this.getPhoto());
            pstmt.setBytes(6, this.getVideo());
            pstmt.setInt(7, 1);
            res = pstmt.executeQuery();
            if (res.next()) {
                this.setIdSociete(res.getInt("id_societe"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (res != null)
                res.close();
            if (pstmt != null)
                pstmt.close();
            if (!keepConnectionOpen)
                con.close();
        }

        return this;
    }

    public Societe[] getAll(Connection con) throws Exception {
        Vector<Societe> liste = new Vector<Societe>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM societe WHERE ETAT=1";
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                Utilisateur utilisateur = new Utilisateur().getById(result.getInt(2), null);
                String nom = result.getString(3);
                String titre = result.getString("titre");
                String histoire = result.getString("histoire");
                byte[] photo = result.getBytes("photo");
                byte[] video = result.getBytes("video");
                int etat = result.getInt("etat");
                Societe societe = new Societe(id, nom, utilisateur, titre, histoire, photo, video, etat);
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
        Societe[] societes = new Societe[liste.size()];
        liste.toArray(societes);
        return societes;
    }

    public Produit[] getAllProduits(Connection con) throws Exception {
        Vector<Produit> liste = new Vector<Produit>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_produits_societe WHERE id_societe=" + this.getIdSociete();
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                Categorie categorie = new Categorie().getById(result.getInt(2), null);
                Societe societe = new Societe().getById(result.getInt(3), null);
                String intitule = result.getString(4);
                int etat = result.getInt(5);
                Produit produit = new Produit(id, intitule, categorie, societe, etat);
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
        this.setProduits(produits);
        return produits;
    }

    public Societe getById(int idSociete, Connection con) throws Exception {
        Societe societe = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM societe WHERE id_societe=" + idSociete;
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id_societe");
                Utilisateur utilisateur = new Utilisateur().getById(result.getInt("id_utilisateur"), null);
                String nom = result.getString(3);
                String titre = result.getString("titre");
                String histoire = result.getString("histoire");
                byte[] photo = result.getBytes("photo");
                byte[] video = result.getBytes("video");
                int etat = result.getInt("etat");
                societe = new Societe(id, nom, utilisateur, titre, histoire, photo, video, etat);
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
        return societe;
    }

    public Societe[] getByUtilisateur(int idUtilisateur, Connection con) throws Exception {
        Vector<Societe> liste = new Vector<Societe>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM societe WHERE id_utilisateur=" + idUtilisateur + " AND etat=1";
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id_societe");
                Utilisateur utilisateur = new Utilisateur().getById(result.getInt("id_utilisateur"), null);
                String nom = result.getString(3);
                String titre = result.getString("titre");
                String histoire = result.getString("histoire");
                byte[] photo = result.getBytes("photo");
                byte[] video = result.getBytes("video");
                int etat = result.getInt("etat");
                Societe societe = new Societe(id, nom, utilisateur, titre, histoire, photo, video, etat);
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
        Societe[] societes = new Societe[liste.size()];
        liste.toArray(societes);
        return societes;
    }

    public void update(Connection con) throws Exception {
        boolean keepConnectionOpen = true;
        Statement stmt = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                keepConnectionOpen = false;
            }

            StringBuilder sql = new StringBuilder("UPDATE societe SET ");
            boolean first = true;

            if (this.getNom() != null) {
                sql.append("nom='").append(this.getNom()).append("'");
                first = false;
            }
            if (this.getTitre() != null) {
                if (!first)
                    sql.append(", ");
                sql.append("titre='").append(this.getTitre()).append("'");
                first = false;
            }
            if (this.getHistoire() != null) {
                if (!first)
                    sql.append(", ");
                sql.append("histoire='").append(this.getHistoire()).append("'");
            }
            if (this.getPhoto() != null) {
                if (!first)
                    sql.append(", ");
                sql.append("photo='").append(this.getPhoto()).append("'");
            }

            sql.append(" WHERE id_societe=").append(this.getIdSociete());
            System.out.println("SQL: " + sql.toString());

            stmt = con.createStatement();
            stmt.executeUpdate(sql.toString());

        } catch (Exception e) {
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (!keepConnectionOpen)
                con.close();
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
            String sql = "UPDATE societe SET etat=10 WHERE id_societe='" + this.getIdSociete() + "'";
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

    public Societe getSocieteByIdUtilisateur(int idUser, Connection con) throws Exception {
        Societe societe = null;
        boolean valid = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String sql = "SELECT * FROM societe WHERE id_utilisateur = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idUser);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        societe = new Societe(rs.getInt("id_societe"));
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (valid)
                con.close();
        }

        return societe;
    }

}