package my.stp.v3;

import com.evilcorp.stp.STPParser;

import java.util.Scanner;

public class Application {

  static void main() {
    var scan = new Scanner(System.in);
    var stpVisitor = new STPApplicationVisitor();

    while (scan.hasNextLine()) {
      var line = scan.nextLine();
      if (line.equals("quit")) {
        break;
      }

      var optCmd = STPParser.parse(line);

      var cmd = optCmd.orElse(null);
      if (cmd == null) {
        System.out.println("Pas compris");
        continue;
      }

      cmd.accept(stpVisitor);
    }
  }
}
