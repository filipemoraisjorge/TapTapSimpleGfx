package game_objects;

/**
 * Created by filipejorge on 04/03/16.
 */

public class Marker extends GameObject {


    private double velocityX;
    private double velocityY;

    public Marker(Representable representation) {
        super(representation);
        chooseRandomPos();
    }

    public Marker(Representable representation, double velocityX, double velocityY) {
        super(representation);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void chooseRandomPos() {
        velocityX = (Math.random()) + 1;
        velocityY = (Math.random()) + 1;

    }

    public int getRadius() {
        return (int) (getRepresentation().getWidth() / 2);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public int[] getCenterPoint() {
        int[] point = {getX() + getRadius(), getY() + getRadius()};
        return point;
    }
}
