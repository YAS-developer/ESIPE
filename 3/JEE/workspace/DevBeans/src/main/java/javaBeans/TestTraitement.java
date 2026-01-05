package javaBeans;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestTraitement {

    public static void main(String[] args) {
        System.out.println("--- Début du Test Traitement ---");
        BOperations operations = new BOperations();

        try {
            operations.ouvrirConnexion();

            operations.setNoDeCompte("0001");
            operations.setOp("-"); // Testez "-" avec une grosse valeur pour voir l'erreur 24
            operations.setValeur("50.00");

            operations.traiter();

            System.out.println("Opération effectuée avec succès.");
            System.out.println("Nouveau solde : " + operations.getNouveauSolde());

            operations.fermerConnexion();

        } catch (TraitementException e) {
            String msg = MessagesDErreurs.getMessageDErreur(e.getMessage());
            System.out.println("ERREUR : " + msg);
        }
    }
}