package fr.uge.poo.simplegraphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class SimpleGraphicsExample {
  private static void drawAll(Graphics2D graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawRect(100, 20, 40, 140);

    graphics.drawLine(200,150, 300, 300);
    graphics.drawLine(150,150, 250, 300);

  }

  static void main(String[] args) {
    SimpleGraphics area = new SimpleGraphics("area", 800, 600);
    area.clear(Color.WHITE);
    area.render(SimpleGraphicsExample::drawAll);
    //canvas.render(graphics -> drawAll(graphics));
  }
}
