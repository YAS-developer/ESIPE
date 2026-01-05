package fr.uge.poo.paint.ex2;

import java.awt.Graphics2D;


public record Line(int x1, int y1, int x2, int y2) implements Figure {
    @Override
    public void draw(Graphics2D g) {
        g.drawLine(x1, y1, x2, y2);
    }
}
