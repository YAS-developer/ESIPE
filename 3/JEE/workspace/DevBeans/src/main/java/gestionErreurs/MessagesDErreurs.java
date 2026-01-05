package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
    private static HashMap<String, String> message = new HashMap<>();

    static {
        message.put("3", "Problème pour accéder à ce compte client, vérifiez qu'il est bien valide.");
        message.put("21", "Problème d'accès à la base de données, veuillez le signaler à votre administrateur.");
        message.put("22", "Problème après traitement. Le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur.");
        message.put("24", "Opération refusée, débit demandé supérieur au crédit du compte.");
    }

    public static String getMessageDErreur(String code) {
        if (message.containsKey(code)) {
            return message.get(code);
        } else {
            return "Erreur inconnue (Code: " + code + ")";
        }
    }
}