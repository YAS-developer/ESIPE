package fr.uge.poo.logger.q0;

public interface Logger {
  enum Level {
    ERROR, WARNING, INFO
  }

  void log(Level level, String message);

}
