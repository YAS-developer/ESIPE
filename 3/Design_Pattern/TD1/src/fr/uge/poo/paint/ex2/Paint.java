package fr.uge.poo.paint.ex2;

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

    private Paint() {  }

    public static void main(String[] args) throws IOException {
      
        var path = Paths.get("D:\\ESIPE\\3\\Design_Pattern\\TD1\\src\\fr\\uge\\poo\\simplegraphics\\ex2\\draw1.txt");
        var figures = readFigures(path);

       
        SimpleGraphics area = new SimpleGraphics("Paint", 800, 600);
        area.clear(Color.WHITE);
        area.render((Graphics2D g) -> {
            g.setColor(Color.BLACK);
            for (Figure f : figures) {
                f.draw(g);
            }
        });
    }

    private static List<Figure> readFigures(Path path) throws IOException {
        var figures = new ArrayList<Figure>();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(text -> {
                String[] tokens = text.split(" ");
                if (tokens.length == 0) return;
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                figures.add(new Line(x1, y1, x2, y2));
           
            });
        }
        return figures;
    }
}
