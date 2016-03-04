package game_objects;

public class Marker extends GameObject {
    private static final double INCREASED_SPEED = 1.10;

    //private double[] velocity;

    private double velocityX;
    private double velocityY;

    public Marker(Representable representation) {
        super(representation);
        chooseRandomVelocityAndDirection(10);
    }

    public Marker(Representable representation, double velocityX, double velocityY) {
        super(representation);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public void chooseRandomVelocityAndDirection(int maxVelocity) {
        //velocityX = 9;
        //velocityY = 3;
        velocityX = (Math.random() * maxVelocity) + 1;
        velocityY = (Math.random() * maxVelocity) + 1;
        if (Math.random() < 0.5) {
            bounceHorizontal();
        }
        if (Math.random() < 0.5) {
            bounceVertical();
        }
        //velocity = new double[] {velocityX, velocityY};
    }

    public void bounceHorizontal() {
        velocityX = -velocityX * INCREASED_SPEED;
    }

    public void bounceVertical() {
        velocityY = -velocityY * INCREASED_SPEED;
    }

    public void stop() {
        velocityX = 0;
        velocityY = 0;
    }

    public void reset(int x, int y, int maxVelocity) {
        move(x - getX(), y - getY());
        chooseRandomVelocityAndDirection(maxVelocity);
    }

    public void move() {
        this.move((int) velocityX, (int) velocityY);
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
