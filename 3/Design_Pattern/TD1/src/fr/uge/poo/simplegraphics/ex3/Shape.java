package fr.uge.poo.simplegraphics.ex3;


import fr.uge.poo.simplegraphics.ex3.Draw.Rectangle;
import fr.uge.poo.simplegraphics.ex3.Draw.Elipse;
import fr.uge.poo.simplegraphics.ex3.Draw.Line;

public sealed interface Shape permits Line, Rectangle, Elipse {

}
