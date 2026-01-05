package duck;

import java.util.logging.Logger;

public class LoggedDuck implements Duck {

  private final Logger LOG = Logger.getLogger(LoggedDuck.class.getName());

  private final Duck duck;

  public LoggedDuck(Duck duck) {
    this.duck = duck;
  }

  @Override
  public void quack() {
    LOG.info("duck > quack");
    duck.quack();
  }

/*  @Override
  public void quackManyTimes(int n) {
    duck.quackManyTimes(n);
  }*/
}
