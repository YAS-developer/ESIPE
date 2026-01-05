package gestionErreurs;

public class TraitementException extends Exception {
    private static final long serialVersionUID = 1L;

    public TraitementException(String message) {
        super(message); // On stocke le code d'erreur (ex: "21", "3") dans le message de l'exception
    }
}