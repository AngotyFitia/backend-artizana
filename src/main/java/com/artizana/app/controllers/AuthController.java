package com.artizana.app.controllers;

import java.sql.Connection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.artizana.app.models.Utilisateur;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    Utilisateur utilisateur = new Utilisateur();

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        ModelAndView mv = new ModelAndView();
        Connection con = null;

        try {
            Utilisateur user = utilisateur.getUtilisateurByemailPassword(email, password, con);

            if (user != null) {
                session.setAttribute("utilisateur", user);
                mv.setViewName("home");
            } else {
                mv.setViewName("index");
                mv.addObject("error", "Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("index");
            mv.addObject("error", "Erreur interne, réessayez plus tard");
        }

        return mv;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("index");
    }
}
