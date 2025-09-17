package com.artizana.app.controllers.mobile;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artizana.app.models.Facture;
import com.artizana.app.models.MouvementStock;
import com.artizana.app.models.Produit;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("api/mobile")
public class CommandeController {

    @PostMapping("/mouvement-stock")
    public void ajoutStock(@RequestParam("idProduit") int idProduit,
    @RequestParam("quantiteEntree") int quantiteEntree,
    @RequestParam("quantiteSortie") int quantiteSortie) throws Exception {
        MouvementStock mouvementStock = new MouvementStock();
        Produit produit = new Produit().getById(idProduit, null);
        mouvementStock.setProduit(produit);
        mouvementStock.setQuantiteEntree(quantiteEntree);
        mouvementStock.setQuantiteSortie(quantiteSortie);
        System.out.println(mouvementStock.insert(null));
    }

    @PostMapping("/commande-form")
    public void ajoutCommande(@RequestParam("idSociete") int idSociete,
                @RequestParam("idUtilisateur") int idUtilisateur,
                @RequestParam("idProduit") int idProduit, 
                @RequestParam("quantite") int quantite,
                @RequestParam("prix") int prixProduit) throws Exception {
        MouvementStock mouvementStock = new MouvementStock();
        Produit produit = new Produit().getById(idProduit, null);
        mouvementStock.setProduit(produit);
        mouvementStock.setQuantiteEntree(0);
        mouvementStock.setQuantiteSortie(quantite);
        Facture.insertCommande(idUtilisateur, idSociete, idProduit, quantite, prixProduit,null  );
    }
}
