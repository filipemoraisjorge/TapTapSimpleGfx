package org.academiadecodigo.bootcamp.filipejorge.gameobjects;

public class GameObject {

    private Representable representation;

    public GameObject(Representable representation) {
        this.representation = representation;
    }

    public Representable getRepresentation() {
        return representation;
    }

    public void move(int x, int y) {
        representation.move(x, y);
    }

    public void setPos(int x, int y) {
        representation.setPos(x, y);
    }

    public void setCenter(int x, int y) {
        representation.setPos(x - getWidth() / 2, y - getHeight() / 2);
    }

    public int getX() {
        return representation.getX();
    }

    public int getY() {
        return representation.getY();
    }

    public void grow(double dw, double dh) {
        representation.grow(dw, dh);
    }

    public void draw() {
        representation.draw();
    }

    public void fill() {
        representation.fill();
    }

    public void delete() {
        representation.delete();
    }

    public void setColor(TapTapColor color) {
        representation.setColor(color.getR(), color.getG(), color.getB());
    }

    public int getWidth() {
        return getRepresentation().getWidth();
    }

    public int getHeight() {
        return getRepresentation().getHeight();
    }


}
