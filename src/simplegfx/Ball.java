package simplegfx;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;

public class Ball extends SimpleGfxRepresentation {

    public Ball(int x, int y, int radius) {

        super.setShape(new Ellipse(x, y, (2 * radius), (2 * radius)));

    }

}
