package my.stp.v2;

import com.evilcorp.stp.ElapsedTimeCmd;
import com.evilcorp.stp.HelloCmd;
import com.evilcorp.stp.StartTimerCmd;
import com.evilcorp.stp.StopTimerCmd;

public interface STPCommandVisitor {

  void visit(HelloCmd helloCmd);
  void visit(ElapsedTimeCmd elapsedTimeCmd);
  void visit(StartTimerCmd startTimerCmd);
  void visit(StopTimerCmd stopTimerCmd);

}
