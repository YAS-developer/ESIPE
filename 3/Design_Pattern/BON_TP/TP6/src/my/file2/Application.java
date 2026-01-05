package my.file2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Application {

  private static final MyFileSystemVisitor<String, List<String>> VISITOR = new MyFileVisitor();


  public static List<String> findFilesWithExtension(Path directory, String extension) throws IOException {
    var fileOrDir = MyFileSystem.of(directory);
    return fileOrDir.accept(VISITOR, ext -> ext.equals(extension));
  }


  static void main() throws IOException {
    var path = Path.of("");

    var files = findFilesWithExtension(path, "txt");
    System.out.println(files);
  }
}
