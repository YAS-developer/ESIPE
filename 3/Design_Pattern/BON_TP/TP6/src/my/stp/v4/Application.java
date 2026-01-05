package my.stp.v4;

import com.evilcorp.stphipster.STPParser;

import java.util.Scanner;

public class Application {

  static void main() {
    var scan = new Scanner(System.in);
    var stpApplicationProcessor = new STPApplicationProcessor();

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

      stpApplicationProcessor.process(cmd);
    }
  }
}
