package com.artizana.app.controllers.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.artizana.app.models.Categorie;

public class CategorieServlet {

    @GetMapping("/liste-categories")
    public ModelAndView afficherSocietes() throws Exception {
      Categorie categorie = new Categorie();
      System.out.println("Liste des catégories");
      Categorie[] liste = categorie.getAll(null);
      ModelAndView model = new ModelAndView("ajout-produits");
      model.addObject("categories", liste);
      return model;
    }
}
