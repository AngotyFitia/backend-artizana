package com.artizana.app.controllers.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.artizana.app.models.PhotoProduit;
import com.artizana.app.models.PrixProduit;
import com.artizana.app.models.Produit;
import com.artizana.app.models.Societe;
import com.artizana.app.models.Utilisateur;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

import com.artizana.app.models.Categorie;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/web")
public class ProduitServlet {

  @GetMapping("/liste-produits")
  public ModelAndView afficherProduits() throws Exception {
    Produit produit = new Produit();
    System.out.println("Liste des produits");
    Societe societe = new Societe();
    Categorie categorie = new Categorie();
    Produit[] liste = produit.getAll(null);
    Societe[] societes = societe.getAll(null);
    Categorie[] categories = categorie.getAll(null);
    ModelAndView model = new ModelAndView("liste-produits");
    model.addObject("societes", societes);
    model.addObject("categories", categories);
    model.addObject("produits", liste);
    return model;
  }

  @PostMapping("/ajout-produit")
  public ModelAndView traiterAjout(
      @RequestParam("idCategorie") int idCategorie, @RequestParam("intitule") String intitule, HttpSession session)
      throws Exception {
    ModelAndView mv = new ModelAndView();
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
    if (user.getEtat() == 10) {
      Produit produit = new Produit();
      Societe societe = new Societe().getSocieteByIdUtilisateur(user.getIdUtilisateur(), null);
      Categorie categorie = new Categorie().getById(idCategorie, null);
      produit.setSociete(societe);
      produit.setCategorie(categorie);
      produit.setIntitule(intitule);
      produit.insert(null);
      return new ModelAndView("redirect:/api/web/liste-produits");
    }

    mv.setViewName("index");
    mv.addObject("error", "Vous n'avez pas le droit d'ajouter un produit");
    return mv;
  }

  @PostMapping("/produit-photo-form")
  public PhotoProduit ajoutPhoto(@RequestParam("id_produit") int idProduit,
      @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {
    PhotoProduit imagePhoto = new PhotoProduit();
    Produit produit = new Produit();
    produit.setIdProduit(idProduit);
    imagePhoto.setProduit(produit);

    if (photo != null && !photo.isEmpty()) {
      byte[] photoBytes = photo.getBytes();
      System.out.println("Image length: " + photoBytes.length);
      imagePhoto.setPhoto(photoBytes);
    }
    return imagePhoto.insert(null);
  }

  @PostMapping("/produit-prix-form")
  public PrixProduit ajoutPrix(@RequestParam("id_produit") int idProduit, @RequestParam("prix") double prix)
      throws Exception {
    PrixProduit prixProduit = new PrixProduit();
    Produit produit = new Produit();
    produit.setIdProduit(idProduit);
    prixProduit.setProduit(produit);
    prixProduit.setPrix(prix);
    return prixProduit.insert(null);
  }

  @GetMapping("/validation")
  public ModelAndView getListNonValid(HttpSession session) {
    ModelAndView mv = new ModelAndView();
    try {
      Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
      if (utilisateur.getEtat() == 0) {
        mv.addObject("produits", new Produit().getAllNonValid(null));
        mv.setViewName("produits/validation");
      } else {
        mv.addObject("error", "Accès refusé");
        mv.setViewName("index");
      }
    } catch (Exception e) {
      mv.addObject("error", e);
      mv.setViewName("produits/validation");
    }
    return mv;
  }

  @GetMapping("/produit/valider/{id}")
  public ModelAndView validerProduit(@PathVariable("id") int idProduit, HttpSession session) {
    ModelAndView mv = new ModelAndView();
    try {
      Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

      if (utilisateur != null && utilisateur.getEtat() == 0) {
        boolean ok = new Produit().validerProduit(idProduit, null);

        if (ok) {
          mv.addObject("success", "Produit validé avec succès !");
        } else {
          mv.addObject("error", "Échec de la validation du produit.");
        }

        mv.addObject("produits", new Produit().getAllNonValid(null));
        mv.setViewName("produits/validation");

      } else {
        mv.addObject("error", "Accès refusé");
        mv.setViewName("index");
      }

    } catch (Exception e) {
      mv.addObject("error", e.getMessage());
      mv.setViewName("produits/validation");
    }

    return mv;
  }

  @GetMapping("/ajout-stock")
  public ModelAndView ajoutEnStock(HttpSession session, @RequestParam("id") int id) {
    ModelAndView mv = new ModelAndView();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur.getEtat() == 10) {
      mv.addObject("id", id);
      mv.setViewName("produits/stock");
    } else {
      mv.addObject("error", "Accès refusé");
      mv.setViewName("index");
    }
    return mv;
  }

}
