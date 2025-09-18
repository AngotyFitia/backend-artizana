package com.artizana.app.controllers;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.artizana.app.models.Categorie;
import com.artizana.app.models.Societe;
import com.artizana.app.models.Produit;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/mobile")
public class ProduitViewController {

    // @GetMapping("/produit-form")
    // public String afficherFormulaire() {
    // return "ajout-produit";
    // }

    // // @GetMapping("/produits")
    // // public ModelAndView afficherProduits() throws Exception {
    // // Produit produit = new Produit();
    // // Produit[] liste = produit.getAll(null);

    // // ModelAndView mav = new ModelAndView("liste-produits");
    // // mav.addObject("produits", liste);
    // // return mav;
    // // }

    // @PostMapping("/produit-ajout")
    // public ModelAndView traiterFormulaire(HttpServletRequest request) throws
    // Exception {
    // String intitule = request.getParameter("intitule");

    // Produit produit = new Produit();
    // Categorie categorie = new Categorie();
    // categorie.setIdCategorie(1);
    // produit.setCategorie(categorie);

    // Societe societe = new Societe();
    // societe.setIdSociete(3);

    // produit.setSociete(societe);
    // produit.setIntitule(intitule);
    // produit.setEtat(1);
    // produit.insert(null);

    // return new ModelAndView("redirect:/api/mobile/produits");
    // }

    // @GetMapping("/societess")
    // public ModelAndView getListe()throws Exception{
    // Societe societe = new Societe();
    // System.out.println("aaa");
    // Societe[] liste = societe.getAll(null);

    // ModelAndView mav = new ModelAndView("liste-societe");
    // mav.addObject("societes", liste);
    // return mav;
    // }

    // @GetMapping("/societe/image/{id}")
    // public void getImage(@PathVariable int id, HttpServletResponse response)
    // throws IOException {
    // try {
    // Societe societe = new Societe().getById(id, null);
    // byte[] image = societe.getPhoto();

    // if (image != null && image.length > 0) {
    // response.setContentType("image/jpeg");
    // response.getOutputStream().write(image);
    // response.getOutputStream().flush();
    // } else {
    // response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    // }
    // } catch (Exception e) {
    // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    // e.printStackTrace();
    // }
    // }

    // @GetMapping("/societe/video/{id}")
    // public void getVideo(@PathVariable int id, HttpServletResponse response)
    // throws IOException {
    // try {
    // Societe societe = new Societe().getById(id, null);
    // byte[] video = societe.getVideo();

    // if (video != null && video.length > 0) {
    // response.setContentType("video/mp4");
    // response.getOutputStream().write(video);
    // response.getOutputStream().flush();
    // } else {
    // response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    // }
    // } catch (Exception e) {
    // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    // e.printStackTrace();
    // }
    // }

}
