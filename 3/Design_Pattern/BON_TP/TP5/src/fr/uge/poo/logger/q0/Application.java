package fr.uge.poo.logger.q0;

import java.io.IOException;
import java.nio.file.Path;

public class Application {

  static void main(String[] args) throws IOException {

    var pathLogger = new PathLogger(SystemLogger.INSTANCE());
    pathLogger.initWriter(Path.of("logs.txt"));

    Logger consoleLogger = new FilterLogger(pathLogger, Logger.Level.ERROR, Logger.Level.WARNING);

    consoleLogger.log(Logger.Level.INFO, "INFO MESSAGE");
    consoleLogger.log(Logger.Level.WARNING, "INFO WARNING");
    consoleLogger.log(Logger.Level.ERROR, "INFO ERROR");
  }
}
