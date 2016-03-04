package game_objects;

public interface Representable {

    public int getX();

    public int getY();

    public void move(int x, int y);

    public int getHeight();

    public int getWidth();

    public void grow(double dw, double dh);

    public void draw();

}
