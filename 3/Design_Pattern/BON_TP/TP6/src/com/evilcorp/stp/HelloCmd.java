package com.evilcorp.stp;

import my.stp.v2.STPCommandVisitor;

public class HelloCmd implements STPCommand {

  @Override
  public void accept(STPCommandVisitor visitor) {
    visitor.visit(this);
  }
}
