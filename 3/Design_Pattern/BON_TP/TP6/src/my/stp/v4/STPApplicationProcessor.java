package my.stp.v4;

import com.evilcorp.stphipster.*;

import java.time.LocalDateTime;
import java.util.HashMap;

public class STPApplicationProcessor implements STPCommandProcessor {

  private final HashMap<Integer, Long> timers = new HashMap<>();

  @Override
  public void process(STPCommand.HelloCmd helloCmd) {
    System.out.println("Hello the current date is " + LocalDateTime.now());
  }

  @Override
  public void process(STPCommand.ElapsedTimeCmd elapsedTimeCmd) {
    var currentTime =  System.currentTimeMillis();
    for(var timerId : elapsedTimeCmd.timers()){
      var startTime = timers.get(timerId);
      if (startTime==null){
        System.out.println("Unknown timer "+timerId);
        continue;
      }
      System.out.println("Ellapsed time on timerId "+timerId+" : "+(currentTime-startTime)+"ms");
    }
  }

  @Override
  public void process(STPCommand.StartTimerCmd startTimerCmd) {
    var timerId = startTimerCmd.timerId();
    if (timers.get(timerId) != null) {
      System.out.println("Timer " + timerId + " was already started");
      return;
    }
    var currentTime = System.currentTimeMillis();
    timers.put(timerId, currentTime);
    System.out.println("Timer " + timerId + " started");
  }

  @Override
  public void process(STPCommand.StopTimerCmd stopTimerCmd) {
    var timerId = stopTimerCmd.timerId();
    var startTime = timers.get(timerId);
    if (startTime == null) {
      System.out.println("Timer " + timerId + " was never started");
      return;
    }
    var currentTime = System.currentTimeMillis();
    System.out.println("Timer " + timerId + " was stopped after running for " + (currentTime - startTime) + "ms");
    timers.put(timerId, null);
  }
}
