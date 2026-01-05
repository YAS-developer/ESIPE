package my.file;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Design pattern: Composite
public sealed interface MyFileSystem {

  Path path();

  String name();



  record MyFile(Path path, String name, String extension) implements MyFileSystem {

    public static MyFile from(Path path) {
      var name = path.toFile().getName();
      var pointIndex = name.lastIndexOf('.');
      var ext = name.substring(pointIndex + 1);
      return new MyFile(path, name, ext);
    }

  }

  final class MyFolder implements MyFileSystem {

    private final Path path;
    private final String name;
    private List<MyFileSystem> content;

    public MyFolder(Path path, String name, List<MyFileSystem> content) {
      this.path = path;
      this.name = name;
      this.content = content;
    }

    @Override
    public Path path() {
      return path;
    }

    @Override
    public String name() {
      return name;
    }

    public List<MyFileSystem> content() {
      if (content == null) {
        try {
          content = getFileSystems(path, true);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return content;
    }

    public static MyFolder from(Path path) throws IOException {
      var name = path.toFile().getName();
      var content = getFileSystems(path, false);
      return new MyFolder(path, name, content);
    }

    private static MyFolder lazyFrom(Path path) {
      var name = path.toFile().getName();
      return new MyFolder(path, name, null);
    }

    private static List<MyFileSystem> getFileSystems(Path path, boolean isLazy) throws IOException {
      try(var fileList = Files.list(path)) {
        try {
          return fileList.map(path1 -> {
            try {
              if (isLazy) {
                return MyFileSystem.ofLazy(path1);
              } else {
                return MyFileSystem.of(path1);
              }
            } catch (IOException e) {
              throw new UncheckedIOException(e);
            }
          }).toList();
        } catch (UncheckedIOException  e) {
          throw e.getCause();
        }
      }
    }
  }


  static MyFileSystem of(Path path) throws IOException {
    if (!Files.isDirectory(path)) {
      return MyFile.from(path);
    }
    return MyFolder.from(path);
  }

  static MyFileSystem ofLazy(Path path) throws IOException {
    if (!Files.isDirectory(path)) {
      return MyFile.from(path);
    }
    return MyFolder.lazyFrom(path);
  }

}
