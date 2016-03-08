package org.academiadecodigo.bootcamp.filipejorge;

import org.academiadecodigo.bootcamp.filipejorge.gameobjects.*;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

import java.awt.*;

/**
 * Created by filipejorge on 04/03/16.
 */
public class Game {

    private RepresentationFactory factory;

    private final int MARGIN_TOP = 10;
    private final int MARGIN_LEFT = 10;
/*  private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 480;
    */

    //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int SCREEN_WIDTH = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - (MARGIN_LEFT * 2);
    private final int SCREEN_HEIGHT = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight() - 23 - (MARGIN_TOP * 2);

    private boolean mouseClicked;
    private int mouseX;
    private int mouseY;

    private Field field1;
    private Field field2;
    private Marker p1Marker;
    private Marker p2Marker;

    private float fakeReceivedXPerCent = (float) (Math.random() * 100);
    private float fakeReceivedYPerCent = (float) (Math.random() * 100);
    ;

    public Game(RepresentationFactory factory) {
        this.factory = factory;
        init();
    }

    public void init() {

        field1 = new Field(factory.getGameObject(GameObjectType.FIELD, MARGIN_LEFT + 1, MARGIN_TOP + 1, SCREEN_WIDTH / 2, SCREEN_HEIGHT));
        field1.fill();
        field1.setColor(15, 15, 30);

        field2 = new Field(factory.getGameObject(GameObjectType.FIELD, MARGIN_LEFT + 1 + (SCREEN_WIDTH / 2), MARGIN_TOP + 1, SCREEN_WIDTH / 2, SCREEN_HEIGHT));
        field2.fill();
        field2.setColor(35, 15, 15);

        p1Marker = new Marker(factory.getGameObject(GameObjectType.MARKER, field1.getX(), field1.getY(), 30));
        p1Marker.delete();
        p1Marker.setColor(135, 210, 255);

        p2Marker = new Marker(factory.getGameObject(GameObjectType.MARKER, field2.getX(), field2.getY(), 30));
        p2Marker.delete();
        p2Marker.setColor(255, 210, 175);

        TaptapMouseHandler mouseHandler = new TaptapMouseHandler();


        start();

    }

    private void start() {

        boolean markTapped = false;
        boolean playerTurn = false;

        //choose who starts randomly

        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


           /*
           Marker temp = new Marker(factory.getGameObject(GameObjectType.MARKER, field1.getX(), field1.getY(), 30));
            temp.setCenter(field1.percentToX(field2.xToPercent(field2.getRandomXInside(15))),field1.percentToY(field2.yToPercent(field2.getRandomYInside(15))));
           */


            //receive x,y from udp
            //fake, and if udp fails
            if (!playerTurn) {
                System.out.println("received from p2");
                p2Marker.draw();

                //receive a %, need to translate to this field x, y
                p1Marker.setCenter(field1.getWidth() - field1.percentToX(fakeReceivedXPerCent), field1.getY() + field1.percentToY(fakeReceivedYPerCent));
                //p1Marker.setPos(field1.getRandomXInside(p1Marker.getWidth()), field1.getRandomYInside(p1Marker.getHeight()));
                p1Marker.fill();


                playerTurn = true;
                //start timer
            }

            //detect if player1 hit marker
            if (playerTurn && mouseClicked && (Math.hypot(p1Marker.getCenterX() - mouseX, p1Marker.getCenterY() - mouseY) <= p1Marker.getRadius())) {
                markTapped = true;
                mouseClicked = false;
                p1Marker.delete();
                System.out.println("Hit");
            }

            //make player1 set player2 marker position
            if (playerTurn && mouseClicked && markTapped) {
                mouseClicked = false;
                // X, Y as to be in p2 Field
                if (field2.isInside(mouseX, mouseY)) {
                    markTapped = false;
                    int p2X = mouseX;
                    int p2Y = mouseY;
                    p2Marker.setCenter(p2X, p2Y);
                    p2Marker.fill();

                    //sent by UDP to player2, as %

                    fakeReceivedXPerCent = field2.xToPercent(p2X);
                    fakeReceivedYPerCent = field2.yToPercent(p2Y);
                    System.out.println("sent to p2: x " + field2.xToPercent(p2X) + " " + field2.yToPercent(p2Y));

                    playerTurn = false;
                    //stop timer, calc score
                }

            }


        }
    }


    private class TaptapMouseHandler implements MouseHandler {


        private Mouse mouse = new Mouse(this);

        TaptapMouseHandler() {
            init();

        }

        private void init() {
            mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
            mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            mouseX = (int) mouseEvent.getX();
            mouseY = (int) mouseEvent.getY() - 23;
            mouseClicked = true;
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            //not needed so not implemented.
        }
    }
}
