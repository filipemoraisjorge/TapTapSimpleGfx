package game_objects;

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

    public int getWidth() {
        return getRepresentation().getWidth();
    }

    public int getHeight() {
        return getRepresentation().getHeight();
    }

    public int[] getCenterPoint() {
        int[] point = {getX() + getWidth() / 2, getY() + getHeight() / 2};
        return point;
    }

}
