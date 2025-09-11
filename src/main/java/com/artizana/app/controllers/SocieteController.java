package com.artizana.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.artizana.app.models.Societe;
import com.artizana.app.models.Utilisateur;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/project")
public class SocieteController {

   // @GetMapping("/societes")
   // public Societe[] getListe()throws Exception{
   //    Societe societe = new Societe();
   //    System.out.println("aaa");
   //    Societe[] liste = societe.getAll(null);
   //    return liste;
   // }

   @GetMapping("/societe/{id}")
   public Societe getById(@PathVariable String id)throws Exception{
      Societe societe = new Societe();
      societe=societe.getById(Integer.valueOf(id),null);
      return societe;
   }

   @GetMapping("/societe-utilisateur/{id}")
   public Societe[] getByUtilisateur(@PathVariable String id)throws Exception{
      Societe societe = new Societe();
      System.out.println("aaa");
      Societe[] liste = societe.getByUtilisateur(Integer.valueOf(id), null);
         return liste;
   }

   @PostMapping("/societe-form")
   public Societe form( @RequestParam("idUtilisateur") int idUtilisateur,
                        @RequestParam("nom") String nom, @RequestParam("titre") String titre,
                        @RequestParam("histoire") String histoire, @RequestParam("etat") int etat,
                        @RequestParam(value = "photo", required = false) MultipartFile photo,
                        @RequestParam(value = "video", required = false) MultipartFile video
   ) throws Exception {
      Societe societe = new Societe();
      Utilisateur user = new Utilisateur();
      user.setIdUtilisateur(idUtilisateur);
      societe.setUtilisateur(user);
      societe.setNom(nom);
      societe.setTitre(titre);
      societe.setHistoire(histoire);
      societe.setEtat(etat);

      if (photo != null && !photo.isEmpty()) {
         byte[] photoBytes = photo.getBytes();
         System.out.println("Image length: " + photoBytes.length);
         societe.setPhoto(photoBytes); 
     }
     
      if (video != null && !video.isEmpty()) {
         societe.setVideo(video.getBytes());
      }

      return societe.insert(null);
   }


   @PutMapping("/societe-update")
   public void update(@RequestBody Societe societe)throws Exception{
      societe.update(null);
   }

   @DeleteMapping("/societe-delete/{id}")
   public void delete(@PathVariable String id)throws Exception{
      Societe societe=new Societe();
      societe.setIdSociete(Integer.valueOf(id));
      System.out.println(societe.getIdSociete());
      societe.delete(null);
   }
}
