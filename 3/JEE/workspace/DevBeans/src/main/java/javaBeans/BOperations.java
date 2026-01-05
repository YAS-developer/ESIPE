package javaBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Import obligatoire pour l'exercice 5
import gestionErreurs.TraitementException;

public class BOperations {

    // Variables Exercice 2
    private String noDeCompte;
    private String nom;
    private String prenom;
    private BigDecimal solde;

    // Variables Exercice 3
    private BigDecimal ancienSolde;
    private BigDecimal nouveauSolde;
    private BigDecimal valeur;
    private String op;

    // Variables Exercice 4
    private String dateInf;
    private String dateSup;
    private ArrayList<String[]> operationsParDates;

    // Variables techniques
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // --- Getters et Setters ---

    public void setNoDeCompte(String noDeCompte) { this.noDeCompte = noDeCompte; }
    public String getNoDeCompte() { return noDeCompte; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public BigDecimal getSolde() { return solde; }

    public BigDecimal getAncienSolde() { return ancienSolde; }
    public BigDecimal getNouveauSolde() { return nouveauSolde; }
    public String getOp() { return op; }
    public void setOp(String op) { this.op = op; }

    public void setValeur(String valeurStr) {
        try {
            this.valeur = new BigDecimal(valeurStr);
        } catch (NumberFormatException e) {
            this.valeur = BigDecimal.ZERO;
        }
    }
    public String getValeur() {
        if (this.valeur == null) return "0.00";
        return this.valeur.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public void setDateInf(String dateInf) { this.dateInf = dateInf; }
    public String getDateInf() { return dateInf; }
    public void setDateSup(String dateSup) { this.dateSup = dateSup; }
    public String getDateSup() { return dateSup; }
    public ArrayList<String[]> getOperationsParDates() { return operationsParDates; }

    // --- Connexion ---

    // Modifié pour l'exercice 5 : throws TraitementException
    public void ouvrirConnexion() throws TraitementException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/BANQUE?serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            // Affichage technique pour l'admin
            System.out.println("Erreur technique (Admin) - Connexion : " + e.getMessage());
            // Exception fonctionnelle pour l'utilisateur
            throw new TraitementException("21");
        }
    }

    
    public void fermerConnexion() throws TraitementException {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Erreur technique (Admin) - Fermeture : " + e.getMessage());
            throw new TraitementException("22");
        }
    }

    // --- Traitements ---

   
    public void consulter() throws TraitementException {
        try {
            String sql = "SELECT NOM, PRENOM, SOLDE FROM COMPTE WHERE NOCOMPTE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.noDeCompte);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                this.nom = rs.getString("NOM");
                this.prenom = rs.getString("PRENOM");
                this.solde = rs.getBigDecimal("SOLDE");
            } else {
                // Si le compte n'existe pas, c'est une erreur fonctionnelle (Code 3)
                throw new TraitementException("3");
            }
        } catch (SQLException e) {
            System.out.println("Erreur technique (Admin) - Consulter : " + e.getMessage());
            throw new TraitementException("21");
        }
    }

   
    public void traiter() throws TraitementException {
        try {
            // 1. Récupération du solde actuel
            String sqlSelect = "SELECT SOLDE FROM COMPTE WHERE NOCOMPTE = ?";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, this.noDeCompte);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.ancienSolde = rs.getBigDecimal("SOLDE");
            } else {
                // Compte introuvable
                throw new TraitementException("3");
            }

            // 2. Calcul du nouveau solde
            if ("+".equals(this.op)) {
                this.nouveauSolde = this.ancienSolde.add(this.valeur);
            } else if ("-".equals(this.op)) {
                this.nouveauSolde = this.ancienSolde.subtract(this.valeur);
            }

            // 3. Vérification du solde (Code 24)
            if (this.nouveauSolde.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Refusé : solde insuffisant.");
                conn.rollback();
                throw new TraitementException("24");
            } else {
                // 4. Mise à jour en base
                String sqlUpdate = "UPDATE COMPTE SET SOLDE = ? WHERE NOCOMPTE = ?";
                pstmt = conn.prepareStatement(sqlUpdate);
                pstmt.setBigDecimal(1, this.nouveauSolde);
                pstmt.setString(2, this.noDeCompte);
                pstmt.executeUpdate();

                String sqlInsert = "INSERT INTO OPERATIONS (NOCOMPTE, DATE, HEURE, OP, VALEUR) VALUES (?, CURDATE(), CURTIME(), ?, ?)";
                pstmt = conn.prepareStatement(sqlInsert);
                pstmt.setString(1, this.noDeCompte);
                pstmt.setString(2, this.op);
                pstmt.setBigDecimal(3, this.valeur);
                pstmt.executeUpdate();

                conn.commit();
                this.solde = this.nouveauSolde;
            }
        } catch (SQLException e) {
            try { if(conn!=null) conn.rollback(); } catch(SQLException ex) {}
            System.out.println("Erreur technique (Admin) - Traiter : " + e.getMessage());
            throw new TraitementException("21");
        }
    }

    
    public void listerParDates() throws TraitementException {
        try {
            this.operationsParDates = new ArrayList<String[]>();

            String sql = "SELECT DATE, HEURE, OP, VALEUR FROM OPERATIONS " +
                         "WHERE NOCOMPTE = ? AND DATE BETWEEN ? AND ? " +
                         "ORDER BY DATE, HEURE";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.noDeCompte);
            pstmt.setString(2, this.dateInf);
            pstmt.setString(3, this.dateSup);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] ligne = new String[4];
                ligne[0] = rs.getString("DATE");
                ligne[1] = rs.getString("HEURE");
                ligne[2] = rs.getString("OP");
                ligne[3] = rs.getBigDecimal("VALEUR").toString();
                this.operationsParDates.add(ligne);
            }

        } catch (SQLException e) {
            System.out.println("Erreur technique (Admin) - Lister : " + e.getMessage());
            throw new TraitementException("21");
        }
    }
}