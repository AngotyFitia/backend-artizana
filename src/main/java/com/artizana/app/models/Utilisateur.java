package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import com.artizana.utils.PasswordUtils;

public class Utilisateur {
    int idUtilisateur;
    String nom;
    String prenom;
    String email;
    String motDePasse;
    int etat;
    double monnaie;
    Timestamp date_monnaie;

    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public String getNom() {
        return this.nom;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setIdUtilisateur(int idUtilisateur) throws Exception {
        this.idUtilisateur = idUtilisateur;
    }

    public void setNom(String nom) throws Exception {
        if (nom.length() == 0) {
            throw new Exception("Nom de l'utilisateur invalide.");
        }
        this.nom = nom;
    }

    public void setPrenom(String prenom) throws Exception {
        if (prenom.length() == 0) {
            throw new Exception("Prénom de l'utilisateur invalide.");
        }
        this.prenom = prenom;
    }

    public void setEmail(String email) throws Exception {
        if (email.length() == 0) {
            throw new Exception("Email de l'utilisateur invalide.");
        }
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) throws Exception {
        if (motDePasse.length() == 0) {
            throw new Exception("Mot de passe de l'utilisateur invalide.");
        }
        this.motDePasse = motDePasse;
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

    public double getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(double monnaie) {
        this.monnaie = monnaie;
    }

    public Timestamp getDate_monnaie() {
        return date_monnaie;
    }

    public void setDate_monnaie(Timestamp date_monnaie) {
        this.date_monnaie = date_monnaie;
    }

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, int etat) throws Exception {
        this.setIdUtilisateur(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setMotDePasse(motDePasse);
        this.setEtat(etat);
    }

    public Utilisateur getById(int idUtilisateur, Connection con) throws Exception {
        Utilisateur utilisateur = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM utilisateur WHERE id_utilisateur=" + idUtilisateur;
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String nom = result.getString(2);
                System.out.println(nom);
                String prenom = result.getString(3);
                String motDePasse = result.getString(4);
                String email = result.getString(5);
                int etat = result.getInt(6);
                utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, etat);
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
        return utilisateur;
    }

    public Utilisateur getUtilisateurByemailPassword(String email, String password, Connection con) throws Exception {
        Utilisateur user = null;
        boolean valid = false;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }
            System.out.println("MOT"+password);
            // Hash du mot de passe
            String hashedPassword = PasswordUtils.hashPassword(password);
            System.out.println("hash"+hashedPassword);
    
            // Préparer la requête
            String request = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
    
            // Affichage de la requête avant exécution avec les valeurs
            System.out.println("Requête SQL préparée : " + request);
            System.out.println("Avec les paramètres : email = " + email + ", mot_de_passe = " + hashedPassword);
    
            // Préparer le statement avec les paramètres
            PreparedStatement ps = con.prepareStatement(request);
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            
            // Afficher la requête complète avec les valeurs (optionnel)
            String queryWithValues = request
                .replace("?", "'" + email + "'")
                .replace("?", "'" + hashedPassword + "'");
            System.out.println("Requête complète avec les valeurs : " + queryWithValues);
    
            // Exécuter la requête
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Utilisateur();
                user.setIdUtilisateur(rs.getInt("id_utilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                user.setEtat(rs.getInt("etat"));
            }
    
            // Fermer les ressources
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (valid)
                con.close();
        }
        return user;
    }
    

    public boolean ajouterPortefeuille(Connection con) throws Exception {
        boolean valid = false;
        boolean result = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String sql = "INSERT INTO portefeuille (id_utilisateur, monnaie, date) VALUES (?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, this.getIdUtilisateur());
                pst.setDouble(2, this.getMonnaie());
                pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

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

    public Portefeuille[] historiquePortefeuille(int idUtilisateur, Connection con) throws Exception {
        ArrayList<Portefeuille> list = new ArrayList<>();
        boolean valid = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String sql = "SELECT * FROM portefeuille WHERE id_utilisateur = ? ORDER BY date DESC";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idUtilisateur);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_portefeuille");
                    double monnaie = rs.getDouble("monnaie");
                    Timestamp date = rs.getTimestamp("date");
                    Utilisateur user = new Utilisateur().getById(idUtilisateur, con);
                    list.add(new Portefeuille(id, user, monnaie, date));
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (valid)
                con.close();
        }

        return list.toArray(new Portefeuille[0]);
    }

    public Facture[] getFactureParSociete( Connection con) throws Exception{
        Vector<Facture> liste = new Vector<Facture>();
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM v_total_facture WHERE id_utilisateur="+this.getIdUtilisateur();
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id_facture");
                Utilisateur utilisateur = new Utilisateur().getById(result.getInt("id_utilisateur"), null);
                Societe societe = new Societe().getById(result.getInt("id_societe"),null);
                Timestamp date = result.getTimestamp("date");
                int total=result.getInt("total_calcule");
                int etat = result.getInt("etat");
                Facture facture = new Facture(id, utilisateur, societe, date, total, etat);
                liste.add(facture);
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
        Facture[] factures = new Facture[liste.size()];
        liste.toArray(factures);
        return factures;
    }
}
