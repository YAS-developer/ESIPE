package fr.uge.poo.paint.ex3;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paint {

  sealed interface Shape permits Rectangle, Line, Ellipse {
    void draw(Graphics2D graphics);

    static Shape create(String str) {
      String[] tokens = str.split(" ");
      return switch (tokens[0]) {
        case "line" -> Line.create(tokens);
        case "rectangle" -> Rectangle.create(tokens);
        case "ellipse" -> Ellipse.create(tokens);
        default -> throw new IllegalStateException("Unexpected value: " + tokens[0]);
      };
    }
  }

  private record Rectangle(int x, int y, int width, int height) implements Shape {

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawRect(x, y, width, height);
    }

    private static Rectangle create(String[] tokens) {
      int x = Integer.parseInt(tokens[1]);
      int y = Integer.parseInt(tokens[2]);
      int width = Integer.parseInt(tokens[3]);
      int height = Integer.parseInt(tokens[4]);

      return new Rectangle(x, y, width, height);
    }
  }

  private record Ellipse(int x1, int y1, int x2, int y2) implements Shape {

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawOval(x1, y1, x2, y2);
    }

    private static Ellipse create(String[] tokens) {
      int x1 = Integer.parseInt(tokens[1]);
      int y1 = Integer.parseInt(tokens[2]);
      int x2 = Integer.parseInt(tokens[3]);
      int y2 = Integer.parseInt(tokens[4]);

      return new Ellipse(x1, y1, x2, y2);
    }
  }


  private record Line(int x1, int y1, int x2, int y2) implements Shape {

    @Override
    public void draw(Graphics2D graphics) {
      graphics.drawLine(x1, y1, x2, y2);
    }

   private static Line create(String[] tokens) {
      int x1 = Integer.parseInt(tokens[1]);
      int y1 = Integer.parseInt(tokens[2]);
      int x2 = Integer.parseInt(tokens[3]);
      int y2 = Integer.parseInt(tokens[4]);

      return new Line(x1, y1, x2, y2);
    }
  }

  static void drawAll(List<Shape> instructions, Graphics2D graphics2D) {
    graphics2D.setColor(Color.BLACK);

    for (var instr : instructions) {
      instr.draw(graphics2D);
    }
  }

  static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalStateException("paint.jar file");
    }

    var instructions = getInstructions(args);

    SimpleGraphics area = new SimpleGraphics("area", 800, 600);
    area.clear(Color.WHITE);
    area.render(graphics2D -> drawAll(instructions, graphics2D));
  }

  private static ArrayList<Shape> getInstructions(String[] args) throws IOException {
    var path = Paths.get(args[0]);
    var instructions = new ArrayList<Shape>();

    try(var lines = Files.lines(path)) {
      lines.forEach(str -> instructions.add(Shape.create(str)));
    }
    return instructions;
  }

}
