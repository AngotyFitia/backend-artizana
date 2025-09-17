package com.artizana.app.controllers;

import java.sql.Connection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.artizana.app.models.Utilisateur;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@CrossOrigin(origins = "http://localhost:8100", allowCredentials = "true")
@RestController
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

    @PostMapping("/login-mobile")
    public ResponseEntity<?> loginMobile(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        try {
            Utilisateur user = utilisateur.getUtilisateurByemailPassword(email, password, null);

            if (user != null && user.getEtat()==20) {
                session.setAttribute("utilisateur", user);
                return ResponseEntity.ok(user); // Retourne les infos utilisateur au front
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Email ou mot de passe incorrect"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur interne, réessayez plus tard"));
        }
    }

    @GetMapping("/session")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("index");
    }

    @GetMapping("/logout-mobile")
    public ResponseEntity<?> logoutMobile(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // ne crée pas une session si elle n'existe pas

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Déconnexion réussie"); // ou ResponseEntity.noContent().build();
    }
}
