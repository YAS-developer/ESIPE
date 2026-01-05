package fr.uge.poo.duck.exo3;

public class SystemLogger {
    public enum Level {
        ERROR, WARNING, INFO
    }

    public void log(Level level, String message) {
        System.err.println(level + " " + message);
    }
}
