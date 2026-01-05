package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javaBeans.BOperations;
import gestionErreurs.TraitementException;
import gestionErreurs.MessagesDErreurs;




@WebServlet("/GestionOperations")
public class SOperations extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SOperations() { super(); }

    protected void traiterRequete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Récupération ou Création de la Session
        // "true" signifie : créer une session si elle n'existe pas
        HttpSession session = request.getSession(true);
        
        // 2. Récupération du Bean depuis la session (s'il existe déjà)
        BOperations operations = (BOperations) session.getAttribute("monBean");
        
        // Si c'est la première fois, le bean est null, on l'instancie
        if (operations == null) {
            operations = new BOperations();
            // On le stocke immédiatement en session pour les prochaines fois
            session.setAttribute("monBean", operations);
        }

        String messageErreur = null;
        String action = request.getParameter("action");
        String numCompteParam = request.getParameter("NoDeCompte");

        try {
            // 3. Logique de mise à jour du Compte
            // Si l'utilisateur a saisi un NOUVEAU numéro de compte, on met à jour le bean
            if (numCompteParam != null && !numCompteParam.isEmpty()) {
                operations.setNoDeCompte(numCompteParam);
                // On réinitialise les autres données pour éviter les mélanges
                operations.setNom(null); 
                operations.setOperationsParDates(null);
            }
            
            // Si on a un numéro de compte (soit du paramètre, soit de la session)
            if (operations.getNoDeCompte() != null) {
                
                operations.ouvrirConnexion(); // Utilise le Pool (Ex 14)

                // Toujours recharger les infos fraiches (Solde, Nom)
                operations.consulter();

                // Actions spécifiques
                if ("Traiter".equals(action)) {
                    String valeur = request.getParameter("valeur");
                    String op = request.getParameter("op");
                    operations.setValeur(valeur);
                    operations.setOp(op);
                    operations.traiter();
                } 
                else if ("Lister".equals(action)) {
                    String d1 = request.getParameter("dateInf");
                    String d2 = request.getParameter("dateSup");
                    operations.setDateInf(d1);
                    operations.setDateSup(d2);
                    operations.listerParDates();
                }
                else if ("Deconnexion".equals(action)) {
                    // Optionnel : Permettre de changer de compte proprement
                    session.invalidate();
                    operations = new BOperations(); // Reset local
                }
                
                operations.fermerConnexion();
            }
            
        } catch (TraitementException e) {
            messageErreur = MessagesDErreurs.getMessageDErreur(e.getMessage());
            try { operations.fermerConnexion(); } catch (Exception ex) {}
        }

        // 4. Transfert (On n'a plus besoin de "coller" le bean à la request car il est en session)
        // Mais on garde msgErreur en request car l'erreur est ponctuelle
        request.setAttribute("msgErreur", messageErreur);
        
        this.getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        traiterRequete(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        traiterRequete(request, response);
    }
}