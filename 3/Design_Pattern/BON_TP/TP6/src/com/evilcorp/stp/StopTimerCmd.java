package com.evilcorp.stp;

import my.stp.v2.STPCommandVisitor;

public class StopTimerCmd implements STPCommand {

    private int  timerId;

    public StopTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }

  @Override
  public void accept(STPCommandVisitor visitor) {
    visitor.visit(this);
  }
}
