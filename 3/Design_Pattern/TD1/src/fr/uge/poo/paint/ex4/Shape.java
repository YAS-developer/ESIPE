package fr.uge.poo.paint.ex4;

import java.awt.Graphics2D;


public interface Shape {
    void draw(Graphics2D g);
    double centerX();
    double centerY();
}