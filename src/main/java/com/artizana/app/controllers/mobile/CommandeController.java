package com.artizana.app.controllers.mobile;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artizana.app.models.Facture;
import com.artizana.app.models.MouvementStock;
import com.artizana.app.models.PhotoProduit;
import com.artizana.app.models.PrixProduit;
import com.artizana.app.models.Produit;
import com.artizana.app.models.Utilisateur;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        mouvementStock.insert(null);
        Facture.insertCommande(idUtilisateur, idSociete, idProduit, quantite, prixProduit, null);
    }

    @PostMapping("/mouvement-stock-web-mobile")
    public ModelAndView ajoutStock(
            @RequestParam("idProduit") int idProduit,
            @RequestParam("quantiteEntree") int quantiteEntree,
            @RequestParam("quantiteSortie") int quantiteSortie,
            @RequestParam(name = "redirect", required = false) String redirect) throws Exception {

        MouvementStock mouvementStock = new MouvementStock();
        Produit produit = new Produit().getById(idProduit, null);
        mouvementStock.setProduit(produit);
        mouvementStock.setQuantiteEntree(quantiteEntree);
        mouvementStock.setQuantiteSortie(quantiteSortie);
        boolean success = mouvementStock.insert(null);

        if ("jsp".equals(redirect)) {
            // Redirection pour JSP
            ModelAndView mv = new ModelAndView("redirect:/api/web/liste-produits");
            if (success)
                mv.addObject("success", "Stock ajouté avec succès !");
            else
                mv.addObject("error", "Erreur lors de l'ajout");
            return mv;
        } else {
            // Réponse JSON pour mobile ou Postman
            return new ModelAndView(new MappingJackson2JsonView(), Map.of("success", success));
        }
    }

    @GetMapping("/liste-facture/{id}")
    public Facture[] getFactures(@PathVariable String id)throws Exception{
        Utilisateur utilisateur = new Utilisateur().getById(Integer.valueOf(id), null);
        Facture[] factures = utilisateur.getFactureParSociete(null);
        return factures;
    }

}
