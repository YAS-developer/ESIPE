package fr.uge.poo.paint.ex3;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public final class Paint {
    private Paint() { }

   
    public record Line(int x1, int y1, int x2, int y2) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public record Rectangle(int x, int y, int width, int height) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawRect(x, y, width, height);
        }
    }

    public record Elipse(int x, int y, int width, int height) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawOval(x, y, width, height);
        }
    }
    /* ---------------------------------- */

    public static void main(String[] args) throws IOException {
      
        Path path = Paths.get("D:\\ESIPE\\3\\Design_Pattern\\TD1\\src\\fr\\uge\\poo\\simplegraphics\\ex3\\draw2.txt");
        List<Shape> shapes = readShapes(path);

        SimpleGraphics area = new SimpleGraphics("Paint", 800, 600);
        area.clear(Color.WHITE);
        area.render((Graphics2D g) -> {
            g.setColor(Color.BLACK);
            for (Shape s : shapes) {
                s.draw(g);
            }
        });
    }

  
    private static List<Shape> readShapes(Path path) throws IOException {
        var shapes = new ArrayList<Shape>();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(text -> {
              
           
                var tokens = text.split(" ");
                if (tokens.length == 0) return;
                var kind = tokens[0].toLowerCase();
              
                    switch (kind) {
                        case "line" ->{
                        	if (tokens.length != 5) throw new IllegalArgumentException("Bad line format: " + text);
                            int lx1 = Integer.parseInt(tokens[1]);
                            int ly1 = Integer.parseInt(tokens[2]);
                            int lx2 = Integer.parseInt(tokens[3]);
                            int ly2 = Integer.parseInt(tokens[4]);
                            shapes.add(new Line(lx1, ly1, lx2, ly2));
                        }
                            
                          
                        case "rectangle" ->{
                        	  if (tokens.length != 5) throw new IllegalArgumentException("Bad rectangle format: " + text);
                              int rx = Integer.parseInt(tokens[1]);
                              int ry = Integer.parseInt(tokens[2]);
                              int rwidth = Integer.parseInt(tokens[3]);
                              int rheight = Integer.parseInt(tokens[4]);
                              shapes.add(new Rectangle(rx, ry, rwidth, rheight));
                        }
                          
                           
                        case "ellipse" ->{
                        	  if (tokens.length != 5) throw new IllegalArgumentException("Bad ellipse format: " + text);
                              int ex = Integer.parseInt(tokens[1]);
                              int ey = Integer.parseInt(tokens[2]);
                              int ewidth = Integer.parseInt(tokens[3]);
                              int eheight = Integer.parseInt(tokens[4]);
                              shapes.add(new Elipse(ex, ey, ewidth, eheight));
                        }
                          
                            
                        default ->
                            throw new IllegalArgumentException("Unknown figure type: " + tokens[0]);
                    }
               
            });
        }
        return shapes;
    }
}
