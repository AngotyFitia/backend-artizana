package com.artizana.app.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artizana.app.models.Utilisateur;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UtilisateurServlet {

    @GetMapping("/historique")
    public ModelAndView historique(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        try {
            if (utilisateur.getEtat() == 10) {

            } else {

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return mv;
    }
}
