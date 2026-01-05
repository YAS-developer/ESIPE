package my.file2;

import java.util.function.Predicate;

public interface MyFileSystemVisitor<C,R> {

  R visit(MyFileSystem.MyFile myFile, Predicate<C> predicate);
  R visit(MyFileSystem.MyFolder myFolder, Predicate<C> predicate);

}


