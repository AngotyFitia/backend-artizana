package com.artizana.app.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.artizana.app.models.Categorie;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/artizana")
public class CategorieController {

    @GetMapping("/categories")
    public ModelAndView getFrontList() {
        ModelAndView mv = new ModelAndView();
        try {
            Categorie[] cat = new Categorie().getAll(null);
            System.out.println("Indice 0" + " " + cat[0].getIntitule());
            mv.addObject("categories", cat);
        } catch (Exception e) {
            mv.addObject("erreur", e);
        }
        mv.setViewName("parametrage/list-categorie");
        return mv;

    }

}
