package com.artizana.app.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Utilisateur {
    int idUtilisateur;
    String nom;
    String prenom;
    String email;
    String motDePasse;
    int etat;

    public int getIdUtilisateur(){
        return this.idUtilisateur;
    }
    public String getNom(){
        return this.nom;
    }
    public int getEtat(){
        return this.etat;
    }
    public void setIdUtilisateur(int idUtilisateur)throws Exception{
        this.idUtilisateur=idUtilisateur;
    }
    public void setNom(String nom)throws Exception{
        if(nom.length()==0){
            throw new Exception("Nom de l'utilisateur invalide.");
        }
        this.nom=nom;
    }
    public void setPrenom(String prenom)throws Exception{
        if(prenom.length()==0){
            throw new Exception("Prénom de l'utilisateur invalide.");
        }
        this.prenom=prenom;
    }
    public void setEmail(String email)throws Exception{
        if(email.length()==0){
            throw new Exception("Email de l'utilisateur invalide.");
        }
        this.email=email;
    }
    public void setMotDePasse(String motDePasse)throws Exception{
        if(motDePasse.length()==0){
            throw new Exception("Mot de passe de l'utilisateur invalide.");
        }
        this.motDePasse=motDePasse;
    }
    public void setEtat(int etat)throws Exception{
        if(etat<0){
            throw new Exception("etat invalide: negatif");
        }
        this.etat=etat;
    }
    
    public void setEtat(String etat)throws Exception{
        int a =Integer.valueOf(etat);
        if(etat.length()==0){
            throw new Exception("etat invalide: null");
        }
        this.setEtat(a);
    }

    public Utilisateur()throws Exception{}
    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, int etat)throws Exception{
        this.setIdUtilisateur(id);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setMotDePasse(motDePasse);
        this.setEtat(etat);
    }

    public Utilisateur getById(int idUtilisateur, Connection con)throws Exception{
        Utilisateur utilisateur= null;
        boolean valid=true;
        Statement state=null;
        ResultSet result=null;
        try {
            if(con==null){
                con=Connect.connectDB();
                valid=false;
            }
            String sql = "SELECT * FROM utilisateur WHERE id_utilisateur="+idUtilisateur;
            state = con.createStatement();
            System.out.println(sql);
            result = state.executeQuery(sql);
            while(result.next()){
                int id= result.getInt(1);
                String nom= result.getString(2);
                System.out.println(nom);
                String prenom= result.getString(3);
                String motDePasse= result.getString(4);
                String email= result.getString(5);
                int etat= result.getInt(6);
                utilisateur = new Utilisateur(id, nom, prenom, email, motDePasse, etat);
            }
        } catch (Exception e) {   
            e.printStackTrace(); 
        }finally{
            try {
                if(state!=null ){ state.close(); }
                if(result!=null ){ result.close(); }
                if(valid==false || con !=null){ con.close(); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return utilisateur;
    }

}
