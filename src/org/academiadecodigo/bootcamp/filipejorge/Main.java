package org.academiadecodigo.bootcamp.filipejorge;

import org.academiadecodigo.bootcamp.filipejorge.network.UDPConnection;
import org.academiadecodigo.bootcamp.filipejorge.simplegfx.SimpleGfxRepresentationFactory;

/**
 * Created by filipejorge on 04/03/16.
 */
public class Main {
    public static void main(String[] args) {


        Game game = new Game(new SimpleGfxRepresentationFactory(), new Integer(args[0]), new Integer(args[1]));


    }
}
