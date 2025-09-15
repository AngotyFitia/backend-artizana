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
import com.artizana.app.models.Categorie;

@CrossOrigin(origins="*", allowedHeaders="*")
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
    public ModelAndView traiterAjout(@RequestParam("idSociete") int idSociete,
                        @RequestParam("idCategorie") int idCategorie, @RequestParam("intitule") String intitule) throws Exception {
        Produit produit = new Produit();
        Societe societe = new Societe().getById(idSociete, null);
        Categorie categorie = new Categorie().getById(idCategorie, null);
        produit.setSociete(societe);
        produit.setCategorie(categorie);
        produit.setIntitule(intitule);
        produit.insert(null);
        return new ModelAndView("redirect:/api/web/liste-produits");
   }

  @PostMapping("/produit-photo-form")
  public PhotoProduit ajoutPhoto(@RequestParam("id_produit") int idProduit,
                      @RequestParam(value = "photo", required = false) MultipartFile photo)throws Exception{
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
  public PrixProduit ajoutPrix(@RequestParam("id_produit") int idProduit, @RequestParam("prix") double prix)throws Exception{
    PrixProduit prixProduit = new PrixProduit();
    Produit produit = new Produit();
    produit.setIdProduit(idProduit);
    prixProduit.setProduit(produit);
    prixProduit.setPrix(prix);
    return prixProduit.insert(null);
  }
   
}
