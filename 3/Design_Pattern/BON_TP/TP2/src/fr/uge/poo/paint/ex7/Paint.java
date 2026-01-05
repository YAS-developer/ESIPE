package fr.uge.poo.paint.ex7;

import fr.uge.poo.paint.ex6.CoolGraphicsAdapter;
import fr.uge.poo.paint.ex6.Draw2000;
import fr.uge.poo.paint.ex6.SimpleGraphicsAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Paint {

  private static ArrayList<Shape> getShapes(Path path) throws IOException {
    var instructions = new ArrayList<Shape>();
    try (var lines = Files.lines(path)) {
      lines.forEach(str -> instructions.add(Shape.create(str)));
    }
    return instructions;
  }

  private static Size calculateSize(List<Shape> shapes) {
    var maxSize = new Size(500, 500);

    maxSize = shapes.stream().map(Shape::size).reduce(maxSize, (s1, s2) -> {
      var width = Math.max(s1.width(), s2.width());
      var height = Math.max(s1.height(), s2.height());
      return new Size(width, height);
    });

    return maxSize;
  }

  static void main(String[] args) throws IOException {
    if (args.length < 1) {
      throw new IllegalStateException("paint.jar file");
    }

    var path = Paths.get(args[0]);

    var shapes = getShapes(path);

    var size = calculateSize(shapes);

    Draw2000 area;
    if (args.length >= 2 && "-legacy".equalsIgnoreCase(args[1])) {
      System.out.println("LEGACY MODE");
      area = new SimpleGraphicsAdapter("Title", size.width(), size.height());
    } else {
      area = new CoolGraphicsAdapter("Title", size.width(), size.height());
    }


    var drawing = Drawing.of(area, shapes);
    drawing.start();
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

    Size size();

    void drawWithColor(Draw2000 area, Draw2000.Color color);

    int distance(int x, int y);
  }

  private record Size(int width, int height) {

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

    @Override
    public final boolean equals(Object o) {
      if (!(o instanceof RectangleShape that)) {
        return false;
      }

      return x1 == that.x1 && y1 == that.y1 && x2 == that.x2 && y2 == that.y2;
    }

    @Override
    public int hashCode() {
      int result = x1;
      result = 31 * result + y1;
      result = 31 * result + x2;
      result = 31 * result + y2;
      return result;
    }

    public Size size() {
      return new Size(x1 + x2, y1 + y2);
    }
  }

  private static final class Rectangle extends RectangleShape implements Shape {
    public Rectangle(int x1, int y1, int x2, int y2) {
      super(x1, y1, x2, y2);
    }

    @Override
    public void drawWithColor(Draw2000 area, Draw2000.Color color) {
      area.drawRect(x1, y1, x2, y2, color);
    }
  }

  private static final class Ellipse extends RectangleShape implements Shape {
    public Ellipse(int x1, int y1, int x2, int y2) {
      super(x1, y1, x2, y2);
    }

    @Override
    public void drawWithColor(Draw2000 area, Draw2000.Color color) {
      area.drawOval(x1, y1, x2, y2, color);
    }
  }

  private record Line(int x1, int y1, int x2, int y2) implements Shape {
    @Override
    public Size size() {
      return new Size(x2, y2);
    }

    @Override
    public void drawWithColor(Draw2000 area, Draw2000.Color color) {
      area.drawLine(x1, y1, x2, y2, color);
    }

    @Override
    public int distance(int xx2, int yy2) {
      var xx1 = (x1 + x2) / 2;
      var yy1 = (y1 + y2) / 2;
      return Shape.distanceBetweenPoints(xx1, yy1, xx2, yy2);
    }
  }

  public static final class Drawing {

    private final List<Shape> shapes;
    private final Draw2000 area;
    private Shape selectedShape = null;

    public Drawing(Draw2000 area, List<Shape> shapes) {
      this.shapes = List.copyOf(shapes);
      this.area = area;
    }

    public static Drawing of(Draw2000 area, List<Shape> shapes) throws IOException {
      return new Drawing(area, List.copyOf(shapes));
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
        drawAll();
      });
    }

    private void drawAll() {
      area.clear(Draw2000.Color.WHITE);
      if (selectedShape != null) {
        selectedShape.drawWithColor(area, Draw2000.Color.ORANGE);
      }
      for (var shape : shapes) {
        if (shape == selectedShape) {
          continue;
        }
        shape.drawWithColor(area, Draw2000.Color.BLACK);
      }
      area.render();
    }

    void start() {
      drawAll();
      initMouseEvents();
    }
  }
}
