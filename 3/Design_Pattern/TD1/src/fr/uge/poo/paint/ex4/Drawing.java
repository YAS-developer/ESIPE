package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public final class Drawing {
    private final List<Shape> shapes;

    public Drawing(List<Shape> shapes) {
        this.shapes = new ArrayList<>(shapes);
    }

    public static Drawing fromFile(Path path) throws IOException {
        var shapes = new ArrayList<Shape>();
        try (var lines = Files.lines(path)) {
            lines.forEach(text -> {
              
                String[] tokens = text.split(" ");
                if (tokens.length == 0) return;

                String kind = tokens[0].toLowerCase();
                
                switch (kind) {
                    case "line" -> {

                        int x1 = Integer.parseInt(tokens[1]);
                        int y1 = Integer.parseInt(tokens[2]);
                        int x2 = Integer.parseInt(tokens[3]);
                        int y2 = Integer.parseInt(tokens[4]);
                        shapes.add(new Paint.Line(x1, y1, x2, y2));
                    }
                    case "rectangle" -> {
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        int w = Integer.parseInt(tokens[3]);
                        int h = Integer.parseInt(tokens[4]);
                        shapes.add(new Paint.Rectangle(x, y, w, h));
                    }
                    case "ellipse" -> {
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        int w = Integer.parseInt(tokens[3]);
                        int h = Integer.parseInt(tokens[4]);
                        shapes.add(new Paint.Elipse(x, y, w, h));
                    }
                    default -> throw new IllegalArgumentException("Unknown figure type: " + tokens[0]);
                }
                
            });
        }
        return new Drawing(shapes);
    }

  
    public void drawAll(Graphics2D g, Optional<Shape> selected) {
        for (Shape s : shapes) {
            if (selected.isPresent() && selected.get() == s) {
                g.setColor(java.awt.Color.ORANGE);
            } else {
                g.setColor(java.awt.Color.BLACK);
            }
            s.draw(g);
        }
    }

    public Optional<Shape> findNearest(int x, int y) {
        if (shapes.isEmpty()) return Optional.empty();
        Shape best = null;
        double bestDistSq = Double.POSITIVE_INFINITY;
        for (var s : shapes) {
            double dx = s.centerX() - x;
            double dy = s.centerY() - y;
            double distSq = dx * dx + dy * dy;
            if (best == null || distSq < bestDistSq) {
                best = s;
                bestDistSq = distSq;
            }
        }
        return Optional.ofNullable(best);
    }
}