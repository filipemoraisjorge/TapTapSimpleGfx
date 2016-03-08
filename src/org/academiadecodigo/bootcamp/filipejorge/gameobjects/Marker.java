package org.academiadecodigo.bootcamp.filipejorge.gameobjects;

/**
 * Created by filipejorge on 04/03/16.
 */

public class Marker extends GameObject {

    public Marker(Representable representation) {
        super(representation);
    }

    public int getRadius() {
        return getRepresentation().getWidth() / 2;
    }

    public int getCenterX() {
        return getX() + getWidth() / 2;
    }

    public int getCenterY() {
        return getY() + getHeight() / 2;
    }

}
