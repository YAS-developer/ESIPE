package fr.uge.poo.paint.ex4;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paint {

  private static ArrayList<Shape> getInstructions(String[] args) throws IOException {
    var path = Paths.get(args[0]);
    var instructions = new ArrayList<Shape>();

    try (var lines = Files.lines(path)) {
      lines.forEach(str -> instructions.add(Shape.create(str)));
    }
    return instructions;
  }

  static void main(String[] args) throws IOException {
    if (args.length != 1) {
      throw new IllegalStateException("paint.jar file");
    }

    var shapes = getInstructions(args);

    SimpleGraphics area = new SimpleGraphics("area", 800, 600);

    var drawing = new Drawing(area, shapes);
    drawing.start();

  }

  private sealed abstract static class RectangleShape {

    final int x1;
    final int y1;
    final int x2;
    final int y2;

    public RectangleShape(int x1, int y1, int x2, int y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }

    public int distance(int xx2, int yy2) {
      var xx1 = x1 + x2 / 2;
      var yy1 = y1 + y2 / 2;
      return Shape.distanceBetweenPoints(xx1, yy1, xx2, yy2);
    }

  }

  public sealed interface Shape permits Ellipse, Line, Rectangle {
    static int distanceBetweenPoints(int x1, int y1, int x2, int y2) {
      var x = x2 - x1;
      var y = y2 - y1;
      return (x * x) + (y * y);
    }

    static Shape create(String str) {
      String[] tokens = str.split(" ");

      int x1 = Integer.parseInt(tokens[1]);
      int y1 = Integer.parseInt(tokens[2]);
      int x2 = Integer.parseInt(tokens[3]);
      int y2 = Integer.parseInt(tokens[4]);

      return switch (tokens[0]) {
        case "line" -> new Line(x1, y1, x2, y2);
        case "rectangle" -> new Rectangle(x1, y1, x2, y2);
        case "ellipse" -> new Ellipse(x1, y1, x2, y2);
        default -> throw new IllegalStateException("Unexpected value: " + tokens[0]);
      };
    }

    void drawWithColor(Graphics2D graphics, Color color);

    int distance(int x, int y);
  }

  private static final class Rectangle extends RectangleShape implements Shape {
    public Rectangle(int x1, int y1, int x2, int y2) {
      super(x1, y1, x2, y2);
    }

    @Override
    public void drawWithColor(Graphics2D graphics, Color color) {
      graphics.setColor(color);
      graphics.drawRect(x1, y1, x2, y2);
    }
  }

  private static final class Ellipse extends RectangleShape implements Shape {
    public Ellipse(int x1, int y1, int x2, int y2) {
      super(x1, y1, x2, y2);
    }

    @Override
    public void drawWithColor(Graphics2D graphics, Color color) {
      graphics.setColor(color);
      graphics.drawOval(x1, y1, x2, y2);
    }
  }

  private record Line(int x1, int y1, int x2, int y2) implements Shape {
    @Override
    public void drawWithColor(Graphics2D graphics, Color color) {
      graphics.setColor(color);
      graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public int distance(int xx2, int yy2) {
      var xx1 = (x1 + x2) / 2;
      var yy1 = (y1 + y2) / 2;
      return Shape.distanceBetweenPoints(xx1, yy1, xx2, yy2);
    }
  }

  public static final class Drawing {

    private final SimpleGraphics area;
    private final List<Shape> shapes;

    private Shape selectedShape = null;

    public Drawing(SimpleGraphics area, List<Shape> shapes) {
      this.area = area;
      this.shapes = List.copyOf(shapes);
    }

    private void calculateId(int x, int y) {
      Shape minShape = null;
      var minDistance = Integer.MAX_VALUE;

      for (var shape : shapes) {
        var distance = shape.distance(x, y);
        if (distance < minDistance) {
          minDistance = distance;
          minShape = shape;
        }
      }
      selectedShape = minShape;
    }

    void initMouseEvents() {
      area.waitForMouseEvents((x, y) -> {
        calculateId(x, y);
        draw();
      });
    }

    private void drawAll(Graphics2D graphics2D) {
      if (selectedShape != null) {
        selectedShape.drawWithColor(graphics2D, Color.ORANGE);
      }

      for (var shape : shapes) {
        if (shape == selectedShape) {
          continue;
        }

        shape.drawWithColor(graphics2D, Color.BLACK);
      }
    }

    private void draw() {
      area.clear(Color.WHITE);
      area.render(this::drawAll);
    }

    void start() {
      draw();
      initMouseEvents();
    }
  }

}
