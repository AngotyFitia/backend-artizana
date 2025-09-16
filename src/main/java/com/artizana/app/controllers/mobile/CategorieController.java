package com.artizana.app.controllers.mobile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.artizana.app.models.Categorie;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/mobile")
public class CategorieController {


    @GetMapping("/liste-categories")
    public Categorie[] afficherProduits() throws Exception {
      Categorie categorie = new Categorie();
      Categorie[] liste = categorie.getAll(null);
      return liste;
    }
}
