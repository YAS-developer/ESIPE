package javaBeans;

import java.util.ArrayList;
import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestListeParDates {

    public static void main(String[] args) {
        System.out.println("--- Test Liste Par Dates ---");
        BOperations operations = new BOperations();

        try {
            operations.ouvrirConnexion();

            operations.setNoDeCompte("0001");
            operations.setDateInf("2004-09-01");
            operations.setDateSup("2025-12-31");

            operations.listerParDates();

            ArrayList<String[]> liste = operations.getOperationsParDates();
            System.out.println("Liste des opérations pour le compte " + operations.getNoDeCompte());
            System.out.println("-------------------------------------------------");

            if (liste != null) {
                for (String[] ligne : liste) {
                    System.out.println(ligne[0] + " | " + ligne[1] + " | " + ligne[2] + " | " + ligne[3]);
                }
            }

            operations.fermerConnexion();

        } catch (TraitementException e) {
            String msg = MessagesDErreurs.getMessageDErreur(e.getMessage());
            System.out.println("ERREUR : " + msg);
        }
    }
}