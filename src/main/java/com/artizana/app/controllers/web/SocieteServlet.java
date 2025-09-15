package com.artizana.app.controllers.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.ModelAndView;

import com.artizana.app.models.Societe;
import com.artizana.app.models.Produit;
import com.artizana.app.models.Utilisateur;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/web")
public class SocieteServlet {

   @GetMapping("/formulaire-societe")
   public ModelAndView afficherForumulaire(){
      ModelAndView model = new ModelAndView("ajout-societe");
      return model;
   }

   @GetMapping("/liste-societes")
   public ModelAndView afficherSocietes() throws Exception {
      Societe societe = new Societe();
      System.out.println("Liste des sociétés");
      Societe[] liste = societe.getAll(null);
      ModelAndView model = new ModelAndView("liste-societes");
      model.addObject("societes", liste);
      return model;
   }

   @GetMapping("/produits-societe/{id}")
   public ModelAndView afficherProduitsSocietes(@PathVariable String id)throws Exception{
      Societe societe = new Societe();
      societe.setIdSociete(Integer.valueOf(id));
      Produit[] produits= societe.getAllProduits(null);
      ModelAndView model = new ModelAndView("liste-societes");
      model.addObject("societes", produits);
      return model;
   }

   @GetMapping("/societe-utilisateur/{id}")
   public ModelAndView afficherSocietesUtilisateur(@PathVariable String id)throws Exception{
      Societe societe = new Societe();
      System.out.println("Liste des sociétés de l'utilisateur"+Integer.valueOf(id));
      Societe[] liste = societe.getByUtilisateur(Integer.valueOf(id), null);
      ModelAndView model = new ModelAndView("societes-utilisateur");
      model.addObject("societes", liste);
      return model;
   }

   @PostMapping("/ajout-societe")
   public ModelAndView traiterAjout(@RequestParam("idUtilisateur") int idUtilisateur,
                        @RequestParam("nom") String nom, @RequestParam("titre") String titre,
                        @RequestParam("histoire") String histoire, @RequestParam(value = "photo", required = true) MultipartFile photo,
                        @RequestParam(value = "video", required = false) MultipartFile video) throws Exception {
      Societe societe = new Societe();
      Utilisateur user = new Utilisateur();
      user.setIdUtilisateur(idUtilisateur);
      societe.setUtilisateur(user);
      societe.setNom(nom);
      societe.setTitre(titre);
      societe.setHistoire(histoire);
      societe.setEtat(1);
      if (photo != null && !photo.isEmpty()) {
         byte[] photoBytes = photo.getBytes();
         System.out.println("Taille de l'image " + photoBytes.length);
         societe.setPhoto(photoBytes); 
      }
      if (video != null && !video.isEmpty()) {
         byte[] videoBytes = video.getBytes();
         System.out.println("Taille de la vidéo " + videoBytes.length);
         societe.setVideo(videoBytes);
      }
      societe.insert(null);
      return new ModelAndView("redirect:/api/web/societe-utilisateur/" + idUtilisateur);
   }

   // @GetMapping("/vue-societe/{id}")
   // public ModelAndView vueSociete(@PathVariable String id)throws Exception{
   //    Societe societe = new Societe().getById(Integer.valueOf(id), null);
   //    System.out.println("Vue de la societe"+ id);
   //    ModelAndView model = new ModelAndView("liste-societes");
   //    model.addObject("societe", societe);
   //    return model;
   // }

   @GetMapping("/vue-societe/{id:\\d+}")
   public ModelAndView vueSociete(@PathVariable String id) throws Exception {
      Societe societe = new Societe().getById(Integer.valueOf(id), null);
      ModelAndView model = new ModelAndView("liste-societes"); 
      Societe[] liste = societe.getAll(null);
      model.addObject("societes", liste);
      model.addObject("societe", societe); // celle qu'on veut éditer
      model.addObject("openModal", true); // indicateur pour JS
      return model;
   }


   @GetMapping("/editer-societe")
   public ModelAndView traiterChangement(@RequestParam("idSociete") String idSociete, @RequestParam("nom") String nom, 
                                 @RequestParam("titre") String titre,@RequestParam("histoire") String histoire
                                 ) throws Exception{
      Societe societe = new Societe();
      int id = Integer.valueOf(idSociete);
      societe.setIdSociete(id);
      societe.setNom(nom);
      societe.setHistoire(histoire);
      societe.setTitre(titre);
      // if (photo != null && !photo.isEmpty()) {
      //    byte[] photoBytes = photo.getBytes();
      //    System.out.println("Taille de l'image " + photoBytes.length);
      //    societe.setPhoto(photoBytes); 
      // }
      societe.update(null);
      return new ModelAndView("redirect:/api/web/vue-societe/"+id);
   }

   @GetMapping("/supprimer-societe/{id}/{idUtilisateur}")
   public ModelAndView delete(@PathVariable String id, @PathVariable String idUtilisateur)throws Exception{
      Societe societe=new Societe();
      societe.setIdSociete(Integer.valueOf(id));
      System.out.println(societe.getIdSociete());
      societe.delete(null);
      // return new ModelAndView("redirect:/api/web/societe-utilisateur/"+idUtilisateur);
      return new ModelAndView("redirect:/api/web/liste-societes");
   }
}
