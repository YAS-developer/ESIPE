package fr.uge.poo.paint.ex5;

public class CoolGraphicsAdapter implements Canvas {
	  private final CoolGraphics graphics;

	  public CoolGraphicsAdapter(String title, int width, int height) {
	    graphics = new CoolGraphics(title, width, height);
	  }

	  @Override
	  public void clear(CanvasColor c) {
	    graphics.repaint(convertColor(c));
	  }

	  @Override
	  public void drawLine(int x1, int y1, int x2, int y2, CanvasColor c) {
	    graphics.drawLine(x1, y1, x2, y2, convertColor(c));
	  }

	  @Override
	  public void drawEllipse(int x, int y, int width, int height, CanvasColor c) {
	    graphics.drawEllipse(x, y, width, height, convertColor(c));
	  }

	  @Override
	  public void waitForMouseClick(MouseClickCallBack callback) {
	    graphics.waitForMouseEvents((x, y) -> callback.onClick(x, y));
	  }

	  private CoolGraphics.ColorPlus convertColor(CanvasColor color) {
	    return switch (color) {
	      case BLACK -> CoolGraphics.ColorPlus.BLACK;
	      case WHITE -> CoolGraphics.ColorPlus.WHITE;
	      case ORANGE -> CoolGraphics.ColorPlus.ORANGE;
	    };
	  }
	}