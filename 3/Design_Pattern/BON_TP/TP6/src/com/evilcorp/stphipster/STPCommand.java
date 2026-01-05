package com.evilcorp.stphipster;

import java.util.List;
import java.util.Objects;

public sealed interface STPCommand {

  record StartTimerCmd(int timerId) implements STPCommand {}
  record StopTimerCmd(int timerId) implements STPCommand {}
  record ElapsedTimeCmd(List<Integer> timers) implements STPCommand {

    public ElapsedTimeCmd(List<Integer> timers) {
      Objects.requireNonNull(timers);
      this.timers = List.copyOf(timers);
    }
  }

  record HelloCmd() implements STPCommand { }
}
