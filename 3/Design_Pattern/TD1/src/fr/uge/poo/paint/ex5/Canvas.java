package fr.uge.poo.paint.ex5;

public interface Canvas {

  enum CanvasColor {
    BLACK,WHITE,ORANGE;
  }

  @FunctionalInterface
  interface MouseClickCallBack{
    void onClick(int x,int y);
  }

  void clear(CanvasColor c);
  void drawLine(int x1,int y1,int x2,int y2,CanvasColor c);
  void drawEllipse(int x,int y,int width,int height,CanvasColor c);
  void waitForMouseClick(MouseClickCallBack callback);

  default void drawRectangle(int x,int y,int width,int height,CanvasColor c){
    drawLine(x,y,x+width,y,c);
    drawLine(x,y+height,x+width,y+height,c);
    drawLine(x,y,x,y+height,c);
    drawLine(x+width,y,x+width,y+height,c);
  }
}