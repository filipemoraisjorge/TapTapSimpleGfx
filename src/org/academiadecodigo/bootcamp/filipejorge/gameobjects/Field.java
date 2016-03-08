package org.academiadecodigo.bootcamp.filipejorge.gameobjects;

/**
 * Created by filipejorge on 04/03/16.
 */
public class Field extends GameObject {
    public Field(Representable representation) {
        super(representation);
    }

    public boolean isInside(int x, int y) {
        return (x >= super.getX() && x <= super.getX() + super.getWidth()) && (y >= super.getY() && y <= super.getY() + super.getHeight());
    }

    public int getRandomXInside(int margin) {
        return super.getX() + ((int) (Math.random() * (super.getWidth() - margin)));
    }

    public int getRandomYInside(int margin) {
        return super.getY() + ((int) (Math.random() * (super.getHeight() - margin)));
    }

    public int getCenterW() {
        return super.getWidth() / 2;
    }

    public int getCenterH() {
        return super.getHeight() / 2;
    }

    public float xToPercent(int x) {

        return ((x - super.getX()) * 100) / super.getWidth();
    }

    public float yToPercent(int y) {
        return ((y - super.getY()) * 100) / super.getHeight();
    }

    public int percentToX(float pc) {

        return Math.round((((super.getWidth()) * pc) / 100));
    }

    public int percentToY(float pc) {
        return Math.round((((super.getHeight()) * pc) / 100));
    }


}
