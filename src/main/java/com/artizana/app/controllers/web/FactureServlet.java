package com.artizana.app.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artizana.app.models.Facture;
import com.artizana.app.models.Utilisateur;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/web")
public class FactureServlet {
    @GetMapping("/factures/nonvalidees")
    public ModelAndView getFacturesNonValidees(
            HttpSession session,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        ModelAndView mv = new ModelAndView();
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

            if (utilisateur == null) {
                mv.setViewName("index");
                mv.addObject("error", "Veuillez vous connecter");
                return mv;
            }

            if (utilisateur.getEtat() != 0) {
                mv.setViewName("index");
                mv.addObject("error", "Accès refusé");
                return mv;
            }

            // Récupérer les factures pour la page actuelle
            Facture[] factures = new Facture().getFactureNonValider(null, page, size);

            // Récupérer le nombre total de factures non validées
            int totalFactures = Facture.countNonValidees(null);
            int totalPages = (int) Math.ceil((double) totalFactures / size);

            mv.addObject("factures", factures);
            mv.addObject("currentPage", page);
            mv.addObject("pageSize", size);
            mv.addObject("totalPages", totalPages);

            mv.setViewName("factures/nonvalidees");

        } catch (Exception e) {
            mv.setViewName("factures/nonvalidees");
            mv.addObject("error", "Erreur : " + e.getMessage());
            e.printStackTrace();
        }

        return mv;
    }

    @GetMapping("/facture/valider")
    public ModelAndView validerFacture(HttpSession session,
            @RequestParam("id") int idFacture,
            RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

            if (utilisateur.getEtat() == 0) {
                Facture facture = new Facture();
                boolean success = facture.validerFacture(idFacture, null);

                if (success) {
                    redirectAttributes.addFlashAttribute("success", "Facture validée avec succès !");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Erreur lors de la validation de la facture.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Accès refusé");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/api/web/factures/nonvalidees");
    }

}
