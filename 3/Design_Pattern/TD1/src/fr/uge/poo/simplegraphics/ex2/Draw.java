package fr.uge.poo.simplegraphics.ex2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Objects;
import java.util.stream.Stream;

import fr.uge.poo.simplegraphics.SimpleGraphics;


public final class Draw {
	
	record Line(int x1, int y1, int x2, int y2) {}
	
	private final static ArrayList<Line> lineList = new ArrayList<Line>();
	
	public Draw(String filePath) throws IOException {
		Objects.requireNonNull(filePath);
		var path = Paths.get(filePath);
       try(Stream<String> lines = Files.lines(path)) {
         lines.forEach(line -> {
        	 String[] tokens = line.split(" ");
             int x1 = Integer.parseInt(tokens[1]);
             int y1 = Integer.parseInt(tokens[2]);
             int x2 = Integer.parseInt(tokens[3]);
             int y2 = Integer.parseInt(tokens[4]);
             lineList.add(new Line(x1, y1, x2, y2));
         });
       }
	}
	
	
	 public static void drawAll(Graphics2D graphics, Draw draw) {
	        for(var line: Draw.lineList) {
	        	graphics.drawLine(line.x1, line.y1, line.x2, line.y2);
	        }
	}
	
	public static void main(String[] args) throws IOException {
		var draw = new Draw("E:\\ESIPE\\3\\Design_Pattern\\TD1\\src\\fr\\uge\\poo\\simplegraphics\\ex2\\draw1.txt");
		SimpleGraphics area = new SimpleGraphics("area", 800, 600);
        area.clear(Color.WHITE);
        area.render(graphics -> drawAll(graphics, draw));
       
	}
}
