package simplegfx;


import game_objects.GameObjectType;
import game_objects.Representable;
import game_objects.RepresentationFactory;

public class SimpleGfxRepresentationFactory implements RepresentationFactory {

    // Field
    public Representable getGameObject(GameObjectType type, int x, int y, int width, int height) {

        Representable shape = null;
        if (type == GameObjectType.FIELD) {
            shape = new Rectangle(x, y, width, height);
        }
        return shape;

    }

    // Marker
    public Representable getGameObject(GameObjectType type, int x, int y, int radius) {

        Representable shape = null;
        if (type == GameObjectType.MARKER) {
            shape = new Ball(x, y, radius);
        }
        return shape;

    }

}
