package javaBeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import gestionErreurs.TraitementException;

public class BUtilisateurs {

    private String login;
    private String passe;
    private String role;
    private Connection conn;

    // --- Getters et Setters ---
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPasse() { return passe; }
    public void setPasse(String passe) { this.passe = passe; }

    public String getRole() { return role; }
    // Pas de setRole public, il est défini par la base de données

    // --- Connexion (Copie de BOperations) ---
    private void ouvrirConnexion() throws TraitementException {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/Banque");
            conn = ds.getConnection();
        } catch (Exception e) {
            throw new TraitementException("21");
        }
    }

    private void fermerConnexion() {
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    // --- Méthode d'Authentification (Exercice 17) ---
    public boolean verifierUtilisateur() throws TraitementException {
        boolean trouve = false;
        ouvrirConnexion();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT ROLE FROM UTILISATEURS WHERE LOGIN = ? AND PASSE = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.login);
            pstmt.setString(2, this.passe);
            
            rs = pstmt.executeQuery();

            if (rs.next()) {
                this.role = rs.getString("ROLE");
                trouve = true;
            }
        } catch (Exception e) {
            throw new TraitementException("21");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            fermerConnexion();
        }
        
        return trouve;
    }
}