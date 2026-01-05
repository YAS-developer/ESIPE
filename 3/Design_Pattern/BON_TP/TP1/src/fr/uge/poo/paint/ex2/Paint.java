package fr.uge.poo.paint.ex2;

import fr.uge.poo.simplegraphics.SimpleGraphics;
import fr.uge.poo.simplegraphics.SimpleGraphicsExample;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paint {


  record Line(int x1, int y1, int x2, int y2) {

    void draw(Graphics2D graphics) {
      graphics.drawLine(x1, y1, x2, y2);
    }

    static Line create(String str) {
      String[] tokens = str.split(" ");
      int x1 = Integer.parseInt(tokens[1]);
      int y1 = Integer.parseInt(tokens[2]);
      int x2 = Integer.parseInt(tokens[3]);
      int y2 = Integer.parseInt(tokens[4]);
      return new Line(x1, y1, x2, y2);
    }
  }

  static void drawAll(List<Line> lines, Graphics2D graphics2D) {
    graphics2D.setColor(Color.BLACK);

    for (var instr : lines) {
      instr.draw(graphics2D);
    }
  }

  static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalStateException("paint.jar file");
    }

    var instructions = getLines(args);

    SimpleGraphics area = new SimpleGraphics("area", 800, 600);
    area.clear(Color.WHITE);
    area.render(graphics2D -> drawAll(instructions, graphics2D));
  }

  private static ArrayList<Line> getLines(String[] args) throws IOException {
    var path = Paths.get(args[0]);

    var instructions = new ArrayList<Line>();

    try(var lines = Files.lines(path)) {
      lines.forEach(str -> instructions.add(Line.create(str)));
    }
    return instructions;
  }

}
