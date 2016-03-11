package org.academiadecodigo.bootcamp.filipejorge;

import org.academiadecodigo.bootcamp.filipejorge.gameobjects.*;
import org.academiadecodigo.bootcamp.filipejorge.network.UDPConnection;
import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;

import java.awt.*;
import java.io.IOException;

/**
 * Created by filipejorge on 04/03/16.
 */
public class Game {

    private RepresentationFactory factory;

    private final int MAX_SCORE = 60000;

    private final int MARGIN_TOP = 10;
    private final int MARGIN_LEFT = 10;
    //private final int SCREEN_WIDTH = 800;
    //private final int SCREEN_HEIGHT = 480;


    //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    //private final int SCREEN_WIDTH = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - (MARGIN_LEFT * 2);
    //private final int SCREEN_HEIGHT = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight() - 23 - (MARGIN_TOP * 2);

    private final int SCREEN_WIDTH = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - (MARGIN_LEFT * 2) - 240;
    private final int SCREEN_HEIGHT = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight() - 23 - (MARGIN_TOP * 2) - 240;


    private boolean mouseClicked;
    private int mouseX;
    private int mouseY;

    private Field field1;
    private Field field2;
    private Marker p1Marker;
    private Marker p2Marker;

    private UDPConnection player2Connection;

    //private float fakeReceivedXPerCent = (float) (Math.random() * 100);
    //private float fakeReceivedYPerCent = (float) (Math.random() * 100);

    public Game(RepresentationFactory factory) {
        this.factory = factory;

        init();
    }

    public void init() {
        field1 = new Field(factory.getGameObject(GameObjectType.FIELD, MARGIN_LEFT + 1, MARGIN_TOP + 1, SCREEN_WIDTH / 2, SCREEN_HEIGHT));
        field1.fill();
        field1.setColor(TapTapColor.PLAYER1_INACTIVE);

        field2 = new Field(factory.getGameObject(GameObjectType.FIELD, MARGIN_LEFT + 1 + (SCREEN_WIDTH / 2), MARGIN_TOP + 1, SCREEN_WIDTH / 2, SCREEN_HEIGHT));
        field2.fill();
        field2.setColor(TapTapColor.PLAYER2_INACTIVE);

        player2Connection = new UDPConnection();

        p1Marker = new Marker(factory.getGameObject(GameObjectType.MARKER, field1.getX(), field1.getY(), 30));
        p1Marker.delete();
        p1Marker.setColor(TapTapColor.MARK1);

        p2Marker = new Marker(factory.getGameObject(GameObjectType.MARKER, field2.getX(), field2.getY(), 30));
        p2Marker.delete();
        p2Marker.setColor(TapTapColor.MARK2);


        TaptapMouseHandler mouseHandler = new TaptapMouseHandler();

        start();

    }

    private void start() {

        boolean markTapped = false;
        boolean playerTurn = false;

        int score = 0;
        long startTime = 0L;
        long endTime = 0L;


        if (player2Connection.chooseTurn()) {
            System.out.println("your turn");
            playerTurn = true;
            markTapped = true;
            //should go to phase2

        } else {
            System.out.println("other player turn");
        }

        //game cycle
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //receive x,y from udp
            //receives a string: "XXX.XX YYY.YY"
            //should be transformed to 2 floats
            //they are percentage values, so it can work with different screen sizes.

            //phase1 - player1 has to hit is marker, his marker position is sent by player2
            //receiving data and setting up marker
            if (!playerTurn) {

                String[] receivedString = new String[1];

                try {
                    receivedString = player2Connection.in().split(" ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p2Marker.delete();

                //update score with player2 turnScore
                score -= Integer.parseInt(receivedString[2]);
                System.out.println("score " + score);

                if (score <= -MAX_SCORE) {
                    System.out.println("You lose!");
                }
                if (score >= MAX_SCORE) {
                    System.out.println("YOU WIN!!!");
                }

                //position sent by player2
                float receivedXpercent = new Float(receivedString[0]);
                float receivedYpercent = new Float(receivedString[1]);


                //receive a %, need to translate to this field x, y
                p1Marker.setCenter(field1.getWidth() - field1.percentToX(receivedXpercent), field1.getY() + field1.percentToY(receivedYpercent));
                p1Marker.fill();
                field1.setColor(TapTapColor.PLAYER1_ACTIVE);
                playerTurn = true;

                //start timing for score
                startTime = System.currentTimeMillis();

            }

            //detect if player1 hit marker
            if (playerTurn && mouseClicked && !markTapped &&
                    (Math.hypot(p1Marker.getCenterX() - mouseX, p1Marker.getCenterY() - mouseY) <= p1Marker.getRadius())) {
                markTapped = true;
                mouseClicked = false;
                p1Marker.delete();
                field1.setColor(TapTapColor.PLAYER1_INACTIVE);
                field2.setColor(TapTapColor.PLAYER2_ACTIVE);
                System.out.println("Hit");
            }

            //phase 2 - player 1 sets the player2's marker position and send it
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

                    float sentXpercent = field2.xToPercent(p2X);
                    float sentYpercent = field2.yToPercent(p2Y);

                    try {

                        //stop timing, calc score
                        int turnScore = 0;
                        if (startTime != 0) {
                            endTime = System.currentTimeMillis();
                            turnScore = (int) (endTime - startTime);
                            System.out.println("turnS " + turnScore);
                            score += turnScore;
                        }

                        //send to player2
                        player2Connection.out(sentXpercent + " " + sentYpercent + " " + turnScore);

                    } catch (IOException e) {
                        //couldn't send
                        e.printStackTrace();
                    }
                    playerTurn = false;
                    field2.setColor(TapTapColor.PLAYER2_INACTIVE);

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
