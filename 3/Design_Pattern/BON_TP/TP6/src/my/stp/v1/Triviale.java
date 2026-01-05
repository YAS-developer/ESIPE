package my.stp.v1;

import com.evilcorp.stp.HelloCmd;
import com.evilcorp.stp.STPParser;

import java.util.Scanner;

public class Triviale {


  static void main() {
    var sc = new Scanner(System.in);


    while (sc.hasNextLine()) {
      var line = sc.nextLine();

      var optCmd = STPParser.parse(line);

      var cmd = optCmd.orElse(null);
      if (cmd == null) {
        System.out.println("Pas compris");
        continue;
      }

      if (cmd instanceof HelloCmd helloCmd) {
        System.out.println("Au revoir");
      } else {
        System.out.println("non implémenté");
      }

    }
  }

}
