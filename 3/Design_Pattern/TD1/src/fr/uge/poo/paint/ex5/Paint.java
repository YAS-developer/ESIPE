package fr.uge.poo.paint.ex5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Paint {
	private final List<Shape> shapes;
//	private final Shape currentShape;

	private Paint(List<Shape> shapes, Shape currentShape) {
		this.shapes = shapes;
//		this.currentShape = null;
	}

	private static void drawAll(List<Shape> shapes, Graphics2D graphics2D) {
    graphics2D.setColor(Color.BLACK);
		shapes.forEach(line -> line.draw(graphics2D));
	}

	public static List<Shape> parse(Path path) throws IOException{
		var list = new ArrayList<Shape>();
		try(Stream<String> lines = Files.lines(path)) {
           lines.forEach(line -> {
               String[] tokens = line.split(" ");
               int x1 = Integer.parseInt(tokens[1]);
               int y1 = Integer.parseInt(tokens[2]);
               int x2 = Integer.parseInt(tokens[3]);
               int y2 = Integer.parseInt(tokens[4]);
               switch(tokens[0]) {
              	case "line" -> list.add(new Line(x1, y1, x2, y2));
              	case "rectangle" -> list.add(new Rectangle(x1, y1, x2, y2));
              	case "ellipse" -> list.add(new Ellipse(x1, y1, x2, y2));
              	default -> throw new IllegalArgumentException("Unexpected value: " + tokens[0]);
              };
           });
        }
		return list;
	}

	public static Paint of(Path path) throws IOException {
		var list = parse(path);
		return new Paint(list, null);
	}

  public void highlightNearest(SimpleGraphics area, int x, int y) {
    var nearest = shapes.stream()
            .min(Comparator.comparingInt(s -> s.distance2(x, y)))
            .orElse(null);
    area.render(g -> {
      drawAll(shapes, g);
      if (nearest != null) {
        g.setColor(Color.ORANGE);
        nearest.draw(g);
      }
    });
  }

	public static void main(String[] args) throws IOException {
   SimpleGraphics area = new SimpleGraphics("area", 800, 600);
   Path path = Paths.get("D:\\ESIPE\\3\\Design_Pattern\\TD1\\src\\fr\\uge\\poo\\paint\\ex5\\draw2.txt");
   var shapes = parse(path);

   area.render(graphics -> drawAll(shapes, graphics));
   var drawing = Paint.of(path);
   area.waitForMouseEvents((x, y) -> drawing.highlightNearest(area, x, y));
	}
}