package com.artizana.app.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    Connection con;

    public static Connection connectDB() throws Exception {
        Class.forName("org.postgresql.Driver");

        // Lecture des variables d'environnement
        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");

        // Valeurs par défaut pour le développement local
        if (dbUrl == null) {
            dbUrl = "jdbc:postgresql://db:5432/artizana";
            dbUser = "user";
            dbPassword = "password";
        }

        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        System.out.println(con);
        return con;
    }
}
