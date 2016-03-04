package game_objects;

public interface RepresentationFactory {

    //Representable getGameObject(GameObjectType type);

    // Rectangle
    Representable getGameObject(GameObjectType type, int x, int y, int width, int height);

    // Marker
    Representable getGameObject(GameObjectType type, int x, int y, int radius);
}
