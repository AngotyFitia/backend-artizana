package com.artizana.app.models;

import java.sql.Timestamp;

public class Portefeuille {
    private int idPortefeuille;
    private Utilisateur utilisateur;
    private double monnaie;
    private Timestamp date;

    // Constructeurs, getters et setters
    public Portefeuille() {
    }

    public Portefeuille(int id, Utilisateur utilisateur, double monnaie, Timestamp date) {
        this.idPortefeuille = id;
        this.utilisateur = utilisateur;
        this.monnaie = monnaie;
        this.date = date;
    }

    public int getIdPortefeuille() {
        return idPortefeuille;
    }

    public void setIdPortefeuille(int idPortefeuille) {
        this.idPortefeuille = idPortefeuille;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public double getMonnaie() {
        return monnaie;
    }

    public void setMonnaie(double monnaie) {
        this.monnaie = monnaie;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
