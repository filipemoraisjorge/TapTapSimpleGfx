package org.academiadecodigo.bootcamp.filipejorge.gameobjects;

public interface Representable {

    int getX();

    int getY();

    void move(int relativeX, int relativeY);

    void setPos(int x, int y);

    int getHeight();

    int getWidth();

    void grow(double dw, double dh);

    void draw();

    void fill();

    void delete();

    void setColor(int r, int g, int b);

}
