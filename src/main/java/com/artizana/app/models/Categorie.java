package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class Categorie {
    int idCategorie;
    String intitule;
    int etat;

    public int getIdCategorie() {
        return this.idCategorie;
    }

    public String getIntitule() {
        return this.intitule;
    }

    public int getEtat() {
        return this.etat;
    }

    public void setIdCategorie(int idCategorie) throws Exception {
        this.idCategorie = idCategorie;
    }

    public void setIntitule(String intitule) throws Exception {
        if (intitule.length() == 0) {
            throw new Exception("L'intitulé du categorie ne peut pas être nulle.");
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

    public Categorie() throws Exception {
    }

    public Categorie(int id, String intitule, int etat) throws Exception {
        this.setIdCategorie(id);
        this.setIntitule(intitule);
        this.setEtat(etat);
    }

    public Categorie getById(int idCategorie, Connection con) throws Exception {
        Categorie categorie = null;
        boolean valid = true;
        Statement state = null;
        ResultSet result = null;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }
            String sql = "SELECT * FROM categorie WHERE id_categorie='" + idCategorie + "'";
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String intitule = result.getString(2);
                int etat = result.getInt(3);
                categorie = new Categorie(id, intitule, etat);
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
        return categorie;
    }

    public Categorie[] getAll(Connection con) throws Exception {
        ArrayList<Categorie> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            String sql = "SELECT id_categorie, intitule, etat FROM categorie WHERE etat = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 1);
            System.out.println(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_categorie");
                String intitule = rs.getString("intitule");
                int etat = rs.getInt("etat");

                Categorie categorie = new Categorie(id, intitule, etat);
                list.add(categorie);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (!valid && con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list.toArray(new Categorie[0]);
    }

}
