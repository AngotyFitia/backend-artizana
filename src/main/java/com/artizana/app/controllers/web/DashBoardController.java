package com.artizana.app.controllers.web;

import com.artizana.app.models.Facture;
import com.artizana.app.models.Produit;
import com.artizana.app.models.Connect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;

@Controller
public class DashBoardController {

    @GetMapping("/dashboard/factures")
    public ModelAndView getFacturesDashboard(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "date", required = false) String dateStr) {

        ModelAndView mv = new ModelAndView("dashboard/factures");
        Connection con = null;

        try {
            con = Connect.connectDB();

            // Valeurs par défaut
            LocalDate today = LocalDate.now();
            if (year == null)
                year = today.getYear();
            if (month == null)
                month = today.getMonthValue();
            if (dateStr == null)
                dateStr = today.toString();

            LocalDate date = LocalDate.parse(dateStr);

            // Appel des fonctions métier avec filtres
            List<Map<String, Object>> byDay = Facture.getByDay(con, date);
            List<Map<String, Object>> byMonth = Facture.getByMonth(con, year, month);
            List<Map<String, Object>> byYear = Facture.getByYear(con, year);

            // Ajout dans le model
            mv.addObject("facturesByDay", byDay);
            mv.addObject("facturesByMonth", byMonth);
            mv.addObject("facturesByYear", byYear);

            // Ajout des filtres sélectionnés
            mv.addObject("selectedYear", year);
            mv.addObject("selectedMonth", month);
            mv.addObject("selectedDate", dateStr);

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Erreur lors de la récupération des factures : " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mv.setViewName("dashboard/validation");

        return mv;
    }

    @GetMapping("/dashboard/vola")
    public ModelAndView getVolaDashboard(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "date", required = false) String dateStr) {

        ModelAndView mv = new ModelAndView("dashboard/vola");
        Connection con = null;

        try {
            con = Connect.connectDB();

            LocalDate today = LocalDate.now();
            if (year == null)
                year = today.getYear();
            if (month == null)
                month = today.getMonthValue();
            if (dateStr == null)
                dateStr = today.toString();

            LocalDate date = LocalDate.parse(dateStr);

            List<Map<String, Object>> volaByDay = Facture.getVolaByDay(con, date);
            List<Map<String, Object>> volaByMonth = Facture.getVolaByMonth(con, year, month);
            List<Map<String, Object>> volaByYear = Facture.getVolaByYear(con, year);

            mv.addObject("volaByDay", volaByDay);
            mv.addObject("volaByMonth", volaByMonth);
            mv.addObject("volaByYear", volaByYear);

            mv.addObject("selectedYear", year);
            mv.addObject("selectedMonth", month);
            mv.addObject("selectedDate", dateStr);

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Erreur lors de la récupération des données : " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mv.setViewName("dashboard/vola");
        return mv;
    }

    @GetMapping("/dashboard/produits-vendus")
    public ModelAndView produitsVendus(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "week", required = false) Integer week,
            @RequestParam(name = "month", required = false) Integer month) {

        ModelAndView mv = new ModelAndView("dashboard/produitsVendus");

        Connection con = null;
        try {
            con = Connect.connectDB();

            LocalDate now = LocalDate.now();
            WeekFields wf = WeekFields.ISO;

            // Valeurs par défaut
            if (year == null)
                year = now.getYear();
            if (week == null)
                week = now.get(wf.weekOfWeekBasedYear());
            if (month == null)
                month = now.getMonthValue();

            // Appel des fonctions métier
            List<Map<String, Object>> produitsWeek = Produit.getProduitVenduByWeek(con, year, week);
            List<Map<String, Object>> produitsMonth = Produit.getProduitVenduByMonth(con, year, month);

            // Ajout des résultats et filtres sélectionnés dans le model
            mv.addObject("produitsWeek", produitsWeek);
            mv.addObject("produitsMonth", produitsMonth);
            mv.addObject("selectedYear", year);
            mv.addObject("selectedWeek", week);
            mv.addObject("selectedMonth", month);

        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", "Erreur lors de la récupération des produits vendus : " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception ignored) {
            }
        }
        mv.setViewName("dashboard/chart");
        return mv;
    }

}
