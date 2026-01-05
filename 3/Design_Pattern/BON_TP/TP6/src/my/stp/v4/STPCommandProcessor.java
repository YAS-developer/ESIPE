package my.stp.v4;

import com.evilcorp.stphipster.*;

public interface STPCommandProcessor {

  void process(STPCommand.HelloCmd helloCmd);
  void process(STPCommand.ElapsedTimeCmd elapsedTimeCmd);
  void process(STPCommand.StartTimerCmd startTimerCmd);
  void process(STPCommand.StopTimerCmd stopTimerCmd);

  default void process(STPCommand att){
    switch(att){
      case STPCommand.ElapsedTimeCmd cmd -> process(cmd);
      case STPCommand.StartTimerCmd cmd -> process(cmd);
      case STPCommand.StopTimerCmd cmd -> process(cmd);
      case STPCommand.HelloCmd cmd -> process(cmd);
    }
  }

}
