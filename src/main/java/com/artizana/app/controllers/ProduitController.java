package com.artizana.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.artizana.app.models.Produit;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/web")
public class ProduitController {

    @GetMapping("/produits")
    public Produit[] getListe()throws Exception{
      Produit produit = new Produit();
      System.out.println("aaa");
      Produit[] liste = produit.getAll(null);
      return liste;
    }

    @GetMapping("/produit/{id}")
    public Produit getById(@PathVariable String id)throws Exception{
      Produit produit = new Produit();
      produit=produit.getById(Integer.valueOf(id), null);
      return produit;
    }

    @PostMapping("/produit-form")
    public Produit form(@RequestBody Produit produit)throws Exception{
      return produit.insert(null);
    }

    @PutMapping("/produit-update")
    public void update(@RequestBody Produit produit)throws Exception{
      produit.update(null);
    }

    @DeleteMapping("/produit-delete/{id}")
    public void delete(@PathVariable String id)throws Exception{
      Produit produit=new Produit();
      produit.setIdProduit(Integer.valueOf(id));
      System.out.println(produit.getIdProduit());
      produit.delete(null);
    }
}
