package com.evilcorp.stp;

import my.stp.v2.STPCommandVisitor;

public interface STPCommand {

  void accept(STPCommandVisitor visitor);

}
