package my.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Application {

  private static void collectFiles(MyFileSystem myFileSystem, String extension, List<String> result) {
    switch (myFileSystem) {
      case MyFileSystem.MyFile myFile -> {
        if (myFile.extension().equals(extension)) {
          result.add(myFile.name());
        }
      }
      case MyFileSystem.MyFolder myFolder -> {
        for (var child : myFolder.content()) {
          collectFiles(child, extension, result);
        }
      }
    }
  }

  public static List<String> findFilesWithExtension(Path directory, String extension) throws IOException {
      var fileOrDir = MyFileSystem.ofLazy(directory);
      var matchedFileName = new ArrayList<String>();
      collectFiles(fileOrDir, extension, matchedFileName);
      return matchedFileName;
  }


  static void main() throws IOException {
    var path = Path.of("");

    var files = findFilesWithExtension(path, "txt");
    System.out.println(files);
  }
}
