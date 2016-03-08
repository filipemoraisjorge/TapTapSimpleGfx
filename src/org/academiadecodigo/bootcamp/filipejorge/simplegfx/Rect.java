package org.academiadecodigo.bootcamp.filipejorge.simplegfx;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Rect extends SimpleGfxRepresentation {

    public Rect(int x, int y, int width, int height) {

        super.setShape(new Rectangle(x, y, width, height));

    }

}
