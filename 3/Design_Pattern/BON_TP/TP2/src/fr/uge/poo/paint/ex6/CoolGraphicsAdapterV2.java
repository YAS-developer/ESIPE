package fr.uge.poo.paint.ex6;

import com.evilcorp.coolgraphics.CoolGraphics;

import java.util.ArrayList;
import java.util.List;

public final class CoolGraphicsAdapterV2 implements Draw2000 {

  private final String name;
  private final int width;
  private final int height;
  private final CoolGraphics coolGraphics;

  private Runnable run = () -> {};

  public CoolGraphicsAdapterV2(String name, int width, int height) {
    this.name = name;
    this.width = width;
    this.height = height;

    coolGraphics = new CoolGraphics(name, width, height);
  }

  private CoolGraphics.ColorPlus getColor(Color color) {
    return switch (color) {
      case BLACK -> CoolGraphics.ColorPlus.BLACK;
      case WHITE -> CoolGraphics.ColorPlus.WHITE;
      case ORANGE -> CoolGraphics.ColorPlus.ORANGE;
    };
  }

  @Override
  public void clear(Color color) {
    run = () -> {};
    coolGraphics.repaint(getColor(color));
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2, Color color) {
    var tmpRun = run;
    run = () -> {
      tmpRun.run();
      coolGraphics.drawLine(x1, y1, x2, y2, getColor(color));
    };
  }

  @Override
  public void drawOval(int x1, int y1, int x2, int y2, Color color) {
    var tmpRun = run;
    run = () -> {
      tmpRun.run();
      coolGraphics.drawEllipse(x1, y1, x2, y2, getColor(color));
    };
  }

  @Override
  public void waitForMouseEvents(MouseCallback consumer) {
    coolGraphics.waitForMouseEvents(consumer::onMouseEvent);
  }

  @Override
  public void render() {
    run.run();
  }
}
