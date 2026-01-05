package fr.uge.poo.paint.ex5;

import java.awt.Color;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class SimpleGraphicsAdapter implements Canvas {
	private final SimpleGraphics area;

    public SimpleGraphicsAdapter(String title, int width, int height) {
        this.area = new SimpleGraphics(title, width, height);
    }

    private static Color convert(CanvasColor c) {
        return switch (c) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case ORANGE -> Color.ORANGE;
        };
    }

    @Override
    public void clear(CanvasColor c) {
        area.clear(convert(c));
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, CanvasColor c) {
        area.render(g -> {
            g.setColor(convert(c));
            g.drawLine(x1, y1, x2, y2);
        });
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height, CanvasColor c) {
        area.render(g -> {
            g.setColor(convert(c));
            g.drawOval(x, y, width, height);
        });
    }

    @Override
    public void waitForMouseClick(MouseClickCallBack callback) {
        area.waitForMouseEvents(callback::onClick);
    }
}
