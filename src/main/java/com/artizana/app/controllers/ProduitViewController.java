package com.artizana.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.artizana.app.models.Categorie;
import com.artizana.app.models.Societe;
import com.artizana.app.models.Produit;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/mobile")
public class ProduitViewController {

    @GetMapping("/produit-form")
    public String afficherFormulaire() {
        return "ajout-produit"; 
    }
    
    @GetMapping("/produits")
    public String afficherProduits(HttpServletRequest request) throws Exception {
        Produit produit = new Produit();
        Produit[] liste = produit.getAll(null);
        request.setAttribute("produits", liste);
        return "liste-produits"; 
    }

    @PostMapping("/produit-ajout")
    public String traiterFormulaire(HttpServletRequest request) throws Exception {
        String intitule = request.getParameter("intitule");

        Produit produit = new Produit();
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(1);
        produit.setCategorie(categorie);

        Societe societe = new Societe();
        societe.setIdSociete(8);

        produit.setSociete(societe);
        produit.setIntitule(intitule);
        produit.setEtat(1); 
        produit.insert(null); 
        return "redirect:/api/mobile/produits"; 
    }
}


