package fr.uge.poo.paint.ex5;

public interface Draw2000 {

  enum Color {
    WHITE, BLACK, ORANGE
  }

  @FunctionalInterface
  interface MouseCallback {
    void onMouseEvent(int x, int y);
  }

  void clear(Color color);

  void drawLine(int x1, int y1, int x2, int y2, Color color);
  void drawOval(int x1, int y1, int x2, int y2, Color color);

  void waitForMouseEvents(MouseCallback consumer);

  default void drawRect(int x, int y, int width, int height, Color color) {
    drawLine(x, y, x + width, y, color);
    drawLine(x + width, y, x + width, y + height, color);
    drawLine(x + width, y + height, x, y + height, color);
    drawLine(x, y + height, x, y, color);
  }
}
