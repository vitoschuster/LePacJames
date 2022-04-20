package client.runners;

/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
import client.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.*;
import javafx.util.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Runner {
    // ghost location
    private int xspeed;
    private int yspeed;
    private int widthG;
    private int heightG;
    private int widthB;
    private int heightB;
    private int moveGhost;
    private PixelReader pixelReader = null;
    private Object lock = new Object();
    private int random = 0;
    private Court court;
    
    public static final String IMG_PATH = "../../img/ghost1.png";


    public Ghost(Court court)  {
        // saving data
        super(IMG_PATH);

        // moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1); // generating random 1-4 num for start of movement
    }


    public void setSpeed(int xspeed, int yspeed) {
        this.xspeed = xspeed;
        this.yspeed = yspeed;
    }


    // public void doOpen(Image background, Image ghost) {
    //     System.out.println(background.getWidth() + " " + background.getHeight());
    //     pixelReader = background.getPixelReader(); // getting pixel reader from background
    //     widthB = (int) background.getWidth() - 30; // bg width
    //     heightB = (int) background.getHeight() - 25; // bg height
    //     widthG = (int) ghost.getWidth(); // ghost width
    //     heightG = (int) ghost.getHeight(); // ghost height
    // }

    /**
     * Calling this method in game loop
     * Starting the movement of each ghost based on random number chosen 1-4
     */
    public void update() {
        // Platform.runLater(() -> {
        //     switch (moveGhost) {
        //     //     case 1: // left down
        //     //         x -= xspeed;
        //     //         y += yspeed;
        //     //         break;
        //     //     case 2: // right down
        //     //         x += xspeed;
        //     //         y += yspeed;
        //     //         break;
        //     //     case 3: // left up
        //     //         y -= yspeed;
        //     //         x -= xspeed;
        //     //         break;
        //     //     case 4: // right up
        //     //         x += xspeed;
        //     //         y -= yspeed;
        //     //         break;
        //     //     default:
        //     //         break;
        //     // }
        //     // this.setTranslateX(x);
        //     // this.setTranslateY(y);
        //     checkCollision(); // checking collision with borders and pacman
        // });
    }

    
}