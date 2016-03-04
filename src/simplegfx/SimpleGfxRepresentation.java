package simplegfx;


import game_objects.Representable;
import org.academiadecodigo.simplegraphics.graphics.Movable;
import org.academiadecodigo.simplegraphics.graphics.Shape;

public class SimpleGfxRepresentation implements Representable {

    private Shape shape;

    public void setShape(Shape shape) {
        this.shape = shape;
        shape.draw();
    }

    public int getX() {
        return shape.getX();
    }

    public int getY() {
        return shape.getY();
    }

    public void move(int x, int y) {

        if (shape instanceof Movable) {
            ((Movable) shape).translate(x, y);
        }

    }

    public int getHeight() {
        return shape.getHeight();
    }

    public int getWidth() {
        return shape.getWidth();
    }

    public void grow(double dw, double dh) {
        shape.grow(dw, dh);
    }

    public void draw() {
        shape.draw();
    }

}
