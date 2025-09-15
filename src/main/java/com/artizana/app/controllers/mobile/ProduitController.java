package com.artizana.app.controllers.mobile;

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

import com.artizana.app.models.PhotoProduit;
import com.artizana.app.models.Produit;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/mobile")
public class ProduitController {


  @GetMapping("/liste-produits")
  public Produit[] afficherProduits() throws Exception {
      Produit produit = new Produit();
      Produit[] liste = produit.getAll(null);
      for (Produit p : liste) {
          PhotoProduit[] photos = p.getAllPhotos(null);
          p.setIdProduit(p.getIdProduit());
          p.setPhotosProduit(photos);
          System.err.println("longueur"+photos.length);
      }
      return liste;
  }

  @GetMapping("/produits")
  public Produit getListe() throws Exception{
    Produit produit = new Produit();
    produit.setIdProduit(2);
    produit.setPhotosProduit(produit.getAllPhotos(null));
    return produit;
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

  @PostMapping("/produit-photo-form")
  public PhotoProduit form(@RequestParam("id_produit") int idProduit,
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
