package my.stp.v2;

import com.evilcorp.stp.STPParser;

import java.util.Scanner;

public class Triviale {


  static void main() {
    var sc = new Scanner(System.in);

    var visitor = new STPVisitor();

    while (sc.hasNextLine()) {
      var line = sc.nextLine();

      var optCmd = STPParser.parse(line);

      var cmd = optCmd.orElse(null);
      if (cmd == null) {
        System.out.println("Pas compris");
        continue;
      }

      cmd.accept(visitor);
    }
  }

}
