package fr.uge.poo.logger.q0;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

public class PathLogger implements Logger, Closeable {

  private BufferedWriter writer;

  private final Logger logger;

  public PathLogger(Logger logger){
    this.logger = logger;
  }

  public PathLogger initWriter(Path path) throws IOException {
    this.writer = Files.newBufferedWriter(path,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND);
    return this;
  }

  public void log(Level level, String message) {
    if (writer == null) {
      throw new IllegalStateException("Writer is null");
    }
    var msg = level + " " + message;
    logger.log(level, message);
    try {
      writer.write(msg);
      writer.newLine();
      writer.flush();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public void close() throws IOException {
    if (writer == null) {
      return;
    }
    writer.close();
  }
}
