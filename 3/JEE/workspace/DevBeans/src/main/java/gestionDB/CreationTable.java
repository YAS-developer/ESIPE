package gestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreationTable {

    public static void main(String[] args) {
        // Paramètres de connexion 
        String url = "jdbc:mysql://localhost:3306/BANQUE?serverTimezone=UTC";
        String utilisateur = "root";
        String motDePasse = ""; // Mettez votre mot de passe ici

        Connection connexion = null;
        Statement stmt = null;

        try {
            // 1. Chargement du driver JDBC (optionnel avec les versions récentes de Java mais conseillé)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver MySQL introuvable. Avez-vous ajouté le JAR au Build Path ?");
                e.printStackTrace();
                return;
            }

            // 2. Connexion à la base
            System.out.println("Connexion à la base de données...");
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            stmt = connexion.createStatement();

            // 3. Suppression des tables si elles existent (pour repartir à zéro)
            System.out.println("Suppression des anciennes tables...");
            stmt.executeUpdate("DROP TABLE IF EXISTS OPERATIONS");
            stmt.executeUpdate("DROP TABLE IF EXISTS COMPTE");

            // 4. Création de la table COMPTE [cite: 26, 30]
            System.out.println("Création de la table COMPTE...");
            String createCompte = "CREATE TABLE COMPTE (" +
                    "NOCOMPTE CHAR(4) NOT NULL PRIMARY KEY, " +
                    "NOM VARCHAR(20), " +
                    "PRENOM VARCHAR(20), " +
                    "SOLDE DECIMAL(10,2) NOT NULL" +
                    ")";
            stmt.executeUpdate(createCompte);

            // 5. Création de la table OPERATIONS [cite: 39, 47]
            // Note: Pas de clé primaire demandée pour cette table
            System.out.println("Création de la table OPERATIONS...");
            String createOperations = "CREATE TABLE OPERATIONS (" +
                    "NOCOMPTE CHAR(4) NOT NULL, " +
                    "DATE DATE NOT NULL, " +
                    "HEURE TIME NOT NULL, " +
                    "OP CHAR(1) NOT NULL, " +
                    "VALEUR DECIMAL(10,2) NOT NULL" +
                    ")";
            stmt.executeUpdate(createOperations);

            // 6. Insertion des données dans COMPTE [cite: 12]
            System.out.println("Insertion des données dans COMPTE...");
            stmt.executeUpdate("INSERT INTO COMPTE VALUES ('0001', 'Magnes', 'Charles', 10.00)");
            stmt.executeUpdate("INSERT INTO COMPTE VALUES ('0002', 'Legrand', 'Louis', 205.00)");
            stmt.executeUpdate("INSERT INTO COMPTE VALUES ('0003', 'Labelle', 'Katia', 500.50)");

            // 7. Insertion des données dans OPERATIONS [cite: 17]
            System.out.println("Insertion des données dans OPERATIONS...");
            // Compte 0001
            stmt.executeUpdate("INSERT INTO OPERATIONS VALUES ('0001', '2004-09-05', '12:10:20', '+', 210.00)");
            stmt.executeUpdate("INSERT INTO OPERATIONS VALUES ('0001', '2004-09-06', '09:15:41', '-', 200.00)");
            // Compte 0002
            stmt.executeUpdate("INSERT INTO OPERATIONS VALUES ('0002', '2004-09-06', '10:30:21', '+', 205.00)");
            // Compte 0003
            stmt.executeUpdate("INSERT INTO OPERATIONS VALUES ('0003', '2004-10-01', '11:01:48', '+', 200.00)");
            stmt.executeUpdate("INSERT INTO OPERATIONS VALUES ('0003', '2004-10-03', '08:30:02', '+', 300.50)");

            System.out.println("Terminé avec succès !");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            try {
                if (stmt != null) stmt.close();
                if (connexion != null) connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}