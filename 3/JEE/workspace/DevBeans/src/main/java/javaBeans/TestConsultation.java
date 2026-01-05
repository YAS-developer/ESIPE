package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestConsultation {

    public static void main(String[] args) {
        BOperations operations = new BOperations();

        try {
            operations.ouvrirConnexion();
            
            // Vous pouvez changer "0001" par "9999" pour tester l'erreur code 3
            operations.setNoDeCompte("0001"); 
            
            operations.consulter();

            System.out.println("--- Résultat de la consultation ---");
            System.out.println("N° de compte : " + operations.getNoDeCompte());
            System.out.println("Nom : " + operations.getNom());
            System.out.println("Prénom : " + operations.getPrenom());
            System.out.println("Solde : " + operations.getSolde());

            operations.fermerConnexion();

        } catch (TraitementException e) {
            // Récupération du message convivial
            String msg = MessagesDErreurs.getMessageDErreur(e.getMessage());
            System.out.println("ERREUR : " + msg);
        }
    }
}