package fr.uge.poo.paint.ex5;

import java.awt.*;

public record Ellipse(int x1, int y1, int x2, int y2) implements Shape{
  @Override
  public void draw(Graphics2D graphics2D) {
    graphics2D.drawOval(x1, y1, x2, y2);
  }
  @Override
  public int distance2(int x, int y) {
    int cx = x1 + x2 / 2;
    int cy = y1 + y2 / 2;
    int dx = x - cx;
    int dy = y - cy;
    return dx * dx + dy * dy;
  }
}
