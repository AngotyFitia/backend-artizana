package com.artizana.app.models;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {

    public static Connection connectDB() throws Exception {
        // Charger le driver PostgreSQL
        Class.forName("org.postgresql.Driver");

        // Récupérer les variables d'environnement Render
        String dbUrl = System.getenv("DATABASE_URL");
        String dbUser = System.getenv("DATABASE_USER");
        String dbPassword = System.getenv("DATABASE_PASSWORD");

        // Si DATABASE_URL est défini (Render), le convertir en JDBC
        if (dbUrl != null && !dbUrl.startsWith("jdbc:")) {
            URI dbUri = new URI(dbUrl);

            // Extraire user et password de l'URL
            dbUser = dbUri.getUserInfo().split(":")[0];
            dbPassword = dbUri.getUserInfo().split(":")[1];

            String dbHost = dbUri.getHost();
            int dbPort = dbUri.getPort();
            String dbName = dbUri.getPath().substring(1);

            // Construire l'URL JDBC correcte avec SSL
            dbUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName + "?sslmode=require";
        }

        // Connexion à la base
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        System.out.println("Connected to DB: " + con);
        return con;
    }
}
