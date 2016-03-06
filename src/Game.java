import game_objects.Field;
import game_objects.GameObjectType;
import game_objects.Marker;
import game_objects.RepresentationFactory;

/**
 * Created by filipejorge on 04/03/16.
 */
public class Game {

    private RepresentationFactory factory;

    private final int MARGIN_TOP = 10;
    private final int MARGIN_LEFT = 10;
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 480;


    public Game(RepresentationFactory factory) {
        this.factory = factory;
        init();
    }

    public void init() {
        Field field = new Field(factory.getGameObject(GameObjectType.FIELD, MARGIN_TOP, MARGIN_LEFT, SCREEN_WIDTH, SCREEN_HEIGHT));

        Marker player1Marker = new Marker(factory.getGameObject(GameObjectType.MARKER, 200, 240, 30));


    }
}
