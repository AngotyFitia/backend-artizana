package com.artizana.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    public void setIdFacture(int idFacture) throws Exception {
        this.idFacture = idFacture;
    }

    public void setUtilisateur(Utilisateur utilisateur) throws Exception {
        this.utilisateur = utilisateur;
    }

    public void setSociete(Societe societe) throws Exception {
        this.societe = societe;
    }

    public void setDate(Timestamp date) throws Exception {
        this.date = date;
    }

    public void setTotal(int total) throws Exception {
        this.total = total;
    }

    public void setEtat(int etat) throws Exception {
        this.etat = etat;
    }

    public void setDetails(DetailsFacture[] details) throws Exception {
        this.details = details;
    }

    public Facture() throws Exception {
    }

    public Facture(int idFacture, Utilisateur utilisateur, Societe societe, Timestamp date, int total, int etat)
            throws Exception {
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
            if (res != null)
                res.close();
            if (pstmt != null)
                pstmt.close();
            if (!keepConnectionOpen)
                con.close();
        }
        return this;
    }

    public static int insertCommande(int idUtilisateur, int idSociete, int idProduit, int quantite, int prixProduit,
            Connection con) throws Exception {
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
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (!keepConnectionOpen && con != null)
                con.close();
        }
        return idFacture;
    }
    
    public Facture[] getFactureNonValider(Connection con, int page, int pageSize) throws Exception {
        ArrayList<Facture> list = new ArrayList<>();
        boolean valid = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            int offset = (page - 1) * pageSize;

            String sql = "SELECT * FROM facture WHERE etat = ? ORDER BY \"date\" ASC LIMIT ? OFFSET ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, 0);
                pst.setInt(2, pageSize); // nombre de lignes par page
                pst.setInt(3, offset); // décalage

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int idFacture = rs.getInt("id_facture");
                    Utilisateur user = new Utilisateur().getById(rs.getInt("id_utilisateur"), null);
                    Societe societe = new Societe().getById(rs.getInt("id_societe"), null);
                    Timestamp date = rs.getTimestamp("date");
                    int total = rs.getInt("total");
                    int etat = rs.getInt("etat");

                    Facture facture = new Facture(idFacture, user, societe, date, total, etat);
                    list.add(facture);
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (valid && con != null)
                con.close();
        }

        return list.toArray(new Facture[0]);
    }

    public boolean validerFacture(int idFacture, Connection con) throws Exception {
        boolean valid = false;
        boolean result = false;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }

            String sql = "UPDATE facture SET etat = 10 WHERE id_facture = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idFacture);
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (valid && con != null) {
                con.close();
            }
        }

        return result;
    }

    // Compter total factures non validées
    public static int countNonValidees(Connection con) throws Exception {
        boolean valid = false;
        int count = 0;
        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = true;
            }
            String sql = "SELECT COUNT(*) FROM facture WHERE etat = 0";
            try (Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {
                if (rs.next())
                    count = rs.getInt(1);
            }
        } finally {
            if (valid && con != null)
                con.close();
        }
        return count;
    }

    public double getCommission(Connection connection) throws Exception {
        double commission = 0.0;
        boolean valid = false;
        try {
            if (connection == null) {
                connection = Connect.connectDB();
                valid = true;
            }
            String sql = "SELECT commission FROM commission WHERE date_fin IS NOT NULL ORDER BY date_fin DESC LIMIT 1";
            try (PreparedStatement ps = connection.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    commission = rs.getDouble("commission");
                }

            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (valid)
                connection.close();
        }

        return commission;
    }

    public double getVola(double commission, int total) {
        return total * commission / 100;
    }

    public void insertVola_miditra(Connection con, double vola) {
        boolean valid = true;
        PreparedStatement pst = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            String sql = "INSERT INTO vola_miditra (\"date\", vola) VALUES (CURRENT_TIMESTAMP, ?)";
            pst = con.prepareStatement(sql);
            pst.setDouble(1, vola);

            int rows = pst.executeUpdate();
            System.out.println("Insertion réussie, lignes affectées : " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (!valid && con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Map<String, Object>> getByDay(Connection con, LocalDate filterDate) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            if (filterDate == null) {
                filterDate = LocalDate.now();
            }

            String sql = "SELECT * FROM v_facture_by_day WHERE date = ?";
            ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(filterDate));

            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", rs.getDate("date"));
                map.put("nombre", rs.getInt("nombre"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

    public static List<Map<String, Object>> getByYear(Connection con, Integer filterYear) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            if (filterYear == null) {
                filterYear = LocalDate.now().getYear();
            }

            String sql = "SELECT * FROM v_facture_by_year WHERE annee = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, filterYear);

            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("annee", rs.getInt("annee"));
                map.put("nb_factures", rs.getInt("nb_factures"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

    public static List<Map<String, Object>> getByMonth(Connection con, Integer filterYear, Integer filterMonth)
            throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            if (filterYear == null) {
                filterYear = LocalDate.now().getYear();
            }

            if (filterMonth == null) {
                filterMonth = LocalDate.now().getMonthValue();
            }

            String sql = "SELECT * FROM v_facture_by_month WHERE annee = ? AND mois = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, filterYear);
            ps.setInt(2, filterMonth);

            rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("annee", rs.getInt("annee"));
                map.put("mois", Month.of(rs.getInt("mois")).getDisplayName(TextStyle.FULL, Locale.FRENCH));
                map.put("nb_factures", rs.getInt("nb_factures"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

    // Par jour
    public static List<Map<String, Object>> getVolaByDay(Connection con, LocalDate filterDate) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            if (filterDate == null) {
                filterDate = LocalDate.now();
            }

            String sql = "SELECT * FROM v_vola_miditra_day WHERE date = ?";
            ps = con.prepareStatement(sql);
            ps.setDate(1, java.sql.Date.valueOf(filterDate));
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", rs.getDate("date"));
                map.put("vola", rs.getDouble("vola"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

    // Par mois
    public static List<Map<String, Object>> getVolaByMonth(Connection con, Integer year, Integer month)
            throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            LocalDate today = LocalDate.now();
            if (year == null)
                year = today.getYear();
            if (month == null)
                month = today.getMonthValue();

            String sql = "SELECT * FROM v_vola_miditra_month WHERE annee = ? AND mois = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setInt(2, month);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("annee", rs.getInt("annee"));
                map.put("mois", rs.getInt("mois"));
                map.put("vola", rs.getDouble("vola"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

    // Par année
    public static List<Map<String, Object>> getVolaByYear(Connection con, Integer year) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        boolean valid = true;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            if (con == null) {
                con = Connect.connectDB();
                valid = false;
            }

            if (year == null)
                year = LocalDate.now().getYear();

            String sql = "SELECT * FROM v_vola_miditra_year WHERE annee = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("annee", rs.getInt("annee"));
                map.put("vola", rs.getDouble("vola"));
                list.add(map);
            }

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (!valid && con != null)
                con.close();
        }

        return list;
    }

}
