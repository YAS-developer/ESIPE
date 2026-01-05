package fr.uge.poo.logger.q0;

import java.util.HashSet;
import java.util.Set;

public class SystemLogger implements Logger {

  private static final SystemLogger INSTANCE = new SystemLogger();

  private SystemLogger() {
  }

  public static SystemLogger INSTANCE() {
    return INSTANCE;
  }

  @Override
  public void log(Level level, String message) {
    System.err.println(level + " " + message);
  }
}
