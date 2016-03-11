package org.academiadecodigo.bootcamp.filipejorge.gameobjects;

/**
 * @author Filipe Jorge.
 *         At <Academia de Código_> on 11/03/16.
 */

public enum TapTapColor {
    PLAYER1_INACTIVE(30,80, 200),
    PLAYER1_ACTIVE(255,235,100),
    PLAYER2_INACTIVE(255, 155, 130),
    PLAYER2_ACTIVE(255,235,100),
    MARK1(135, 210, 255),
    MARK2(135, 210, 255);

    private int r;
    private int g;
    private int b;

    TapTapColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}


