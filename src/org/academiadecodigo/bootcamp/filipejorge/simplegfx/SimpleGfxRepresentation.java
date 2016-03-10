package org.academiadecodigo.bootcamp.filipejorge.simplegfx;


import org.academiadecodigo.bootcamp.filipejorge.gameobjects.Representable;
import org.academiadecodigo.simplegraphics.graphics.*;

public class SimpleGfxRepresentation implements Representable {

    private Shape shape;

    public void setShape(Shape shape) {
        this.shape = shape;
        shape.draw();
    }

    @Override
    public int getX() {
        return shape.getX();
    }

    @Override
    public int getY() {
        return shape.getY();
    }

    @Override
    public void move(int x, int y) {

        if (shape instanceof Movable) {
            ((Movable) shape).translate(x, y);
        }

    }

    @Override
    public void setPos(int x, int y) {
        if (shape instanceof Movable) {

            ((Movable) shape).translate(x - getX(), y - getY());
        }
    }

    @Override
    public int getHeight() {
        return shape.getHeight();
    }

    @Override
    public int getWidth() {
        return shape.getWidth();
    }

    @Override
    public void grow(double dw, double dh) {
        shape.grow(dw, dh);
    }

    @Override
    public void draw() {
        shape.draw();
    }

    @Override
    public void fill() {
        if (shape instanceof Fillable) {
            ((Fillable) shape).fill();
        }
    }

    @Override
    public void delete() {
        shape.delete();
    }

    @Override
    public void setColor(int r, int g, int b) {
        if (shape instanceof Colorable) {
            ((Colorable) shape).setColor(new Color(r, g, b));

        }
    }

}
