package my.stp.v2;

import com.evilcorp.stp.ElapsedTimeCmd;
import com.evilcorp.stp.HelloCmd;
import com.evilcorp.stp.StartTimerCmd;
import com.evilcorp.stp.StopTimerCmd;

public class STPVisitor implements STPCommandVisitor{
  @Override
  public void visit(HelloCmd helloCmd) {
    System.out.println("Au revoir");
  }

  @Override
  public void visit(ElapsedTimeCmd elapsedTimeCmd) {
    System.out.println("non implémenté");
  }

  @Override
  public void visit(StartTimerCmd startTimerCmd) {
    System.out.println("non implémenté");
  }

  @Override
  public void visit(StopTimerCmd stopTimerCmd) {
    System.out.println("non implémenté");
  }
}
