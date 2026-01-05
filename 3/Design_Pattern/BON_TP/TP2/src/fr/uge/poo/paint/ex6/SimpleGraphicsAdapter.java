package fr.uge.poo.paint.ex6;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class SimpleGraphicsAdapter implements Draw2000 {

  private final String name;
  private final int width;
  private final int height;

  private final SimpleGraphics simpleGraphics;

  private final ArrayList<Consumer<Graphics2D>> operations = new ArrayList<>();


  public SimpleGraphicsAdapter(String name, int width, int height) {
    this.name = name;
    this.width = width;
    this.height = height;

    simpleGraphics = new SimpleGraphics(name, width, height);
  }

  private java.awt.Color getColor(Color color) {
    return switch (color) {
      case BLACK -> java.awt.Color.BLACK;
      case WHITE -> java.awt.Color.WHITE;
      case ORANGE -> java.awt.Color.ORANGE;
    };
  }

  @Override
  public void clear(Color color) {
    simpleGraphics.clear(getColor(color));
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2, Color color) {
    operations.add(graphics2D -> {
      graphics2D.setColor(getColor(color));
      graphics2D.drawLine(x1,y1,x2,y2);
    });
  }

  @Override
  public void drawRect(int x1, int y1, int x2, int y2, Color color) {
    operations.add(graphics2D -> {
      graphics2D.setColor(getColor(color));
      graphics2D.drawRect(x1,y1,x2,y2);
    });
  }

  @Override
  public void render() {
    var tmpOperations = List.copyOf(operations);
    simpleGraphics.render(graphics2D -> {
      for (var consumer : tmpOperations) {
        consumer.accept(graphics2D);
      }
    });
  }

  @Override
  public void drawOval(int x1, int y1, int x2, int y2, Color color) {
    operations.add(graphics2D -> {
      graphics2D.setColor(getColor(color));
      graphics2D.drawOval(x1,y1,x2,y2);
    });
  }

  @Override
  public void waitForMouseEvents(MouseCallback consumer) {
    simpleGraphics.waitForMouseEvents(consumer::onMouseEvent);
  }
}
