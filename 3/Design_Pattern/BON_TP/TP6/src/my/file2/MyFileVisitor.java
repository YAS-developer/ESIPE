package my.file2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MyFileVisitor implements MyFileSystemVisitor<String, List<String>> {
  @Override
  public List<String> visit(MyFileSystem.MyFile myFile, Predicate<String> predicate) {
    if (predicate.test(myFile.extension())) {
      return List.of(myFile.name());
    }
    return List.of();
  }

  @Override
  public List<String> visit(MyFileSystem.MyFolder myFolder, Predicate<String> predicate) {
    var matchedList = new ArrayList<String>();
    for (var file : myFolder.content()) {
      matchedList.addAll(file.accept(this, predicate));
    }
    return matchedList;
  }
}
