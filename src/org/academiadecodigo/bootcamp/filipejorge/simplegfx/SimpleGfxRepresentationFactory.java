package org.academiadecodigo.bootcamp.filipejorge.simplegfx;


import org.academiadecodigo.bootcamp.filipejorge.gameobjects.GameObjectType;
import org.academiadecodigo.bootcamp.filipejorge.gameobjects.Representable;
import org.academiadecodigo.bootcamp.filipejorge.gameobjects.RepresentationFactory;

public class SimpleGfxRepresentationFactory implements RepresentationFactory {

    // Field
    public Representable getGameObject(GameObjectType type, int x, int y, int width, int height) {

        Representable shape = null;
        if (type == GameObjectType.FIELD) {
            shape = new Rect(x, y, width, height);
        }
        return shape;

    }

    // Marker
    public Representable getGameObject(GameObjectType type, int x, int y, int radius) {

        Representable shape = null;
        if (type == GameObjectType.MARKER) {
            shape = new Circle(x, y, radius);
        }
        return shape;

    }

}
