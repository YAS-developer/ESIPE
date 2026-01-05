package fr.uge.poo.paint.ex4;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


public final class Paint {
    private Paint() { }

    public record Line(int x1, int y1, int x2, int y2) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawLine(x1, y1, x2, y2);
        }

        @Override
        public double centerX() {
            return (x1 + x2) / 2.0;
        }

        @Override
        public double centerY() {
            return (y1 + y2) / 2.0;
        }
    }

    public record Rectangle(int x, int y, int width, int height) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawRect(x, y, width, height);
        }

        @Override
        public double centerX() {
            return x + width / 2.0;
        }

        @Override
        public double centerY() {
            return y + height / 2.0;
        }
    }

    public record Elipse(int x, int y, int width, int height) implements Shape {
        @Override
        public void draw(Graphics2D g) {
            g.drawOval(x, y, width, height);
        }

        @Override
        public double centerX() {
            return x + width / 2.0;
        }

        @Override
        public double centerY() {
            return y + height / 2.0;
        }
    }

    public static void main(String[] args) throws IOException {
 
        var path = Paths.get("D:\\ESIPE\\3\\Design_Pattern\\TD1\\src\\fr\\uge\\poo\\simplegraphics\\ex4\\draw2.txt");
        Drawing drawing = Drawing.fromFile(path);

        var area = new SimpleGraphics("Paint - ex4", 800, 600);
        area.clear(Color.WHITE);

       
        area.render((Graphics2D g) -> drawing.drawAll(g, Optional.empty()));


        area.waitForMouseEvents((mx, my) -> {
            var sel = drawing.findNearest(mx, my);
            area.render((Graphics2D g) -> drawing.drawAll(g, sel));
        });
    }
}