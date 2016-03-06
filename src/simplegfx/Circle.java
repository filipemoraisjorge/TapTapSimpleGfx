package simplegfx;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;

public class Circle extends SimpleGfxRepresentation {

    public Circle(int x, int y, int radius) {

        super.setShape(new Ellipse(x, y, (2 * radius), (2 * radius)));

    }

}
