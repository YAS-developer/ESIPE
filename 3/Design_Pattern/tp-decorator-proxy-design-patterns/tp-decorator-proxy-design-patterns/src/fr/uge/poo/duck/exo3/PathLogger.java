package fr.uge.poo.duck.exo3;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PathLogger implements Closeable {
    private final Path path;
    private final BufferedWriter bufferedWriter;
    private final SystemLogger systemLogger = new SystemLogger();

    public PathLogger(Path path) throws IOException {
        Objects.requireNonNull(path);
        this.path = path;
        bufferedWriter = Files.newBufferedWriter(path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
    }

    public void writeFile(SystemLogger.Level level, String message) throws IOException {
        systemLogger.log(level, message);
        bufferedWriter.write(level.name() + " " + message + System.lineSeparator());
        bufferedWriter.flush();
    }

    @Override
    public void close() throws IOException {
        bufferedWriter.close();
    }
}
