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

    public int moveGhost;
    private int random = 0;

    private Court court;
    public static final String IMG_PATH = "img/ghost";

    public Ghost(Court court, int ghostNum, Point2D pos) {
        // saving data
        super(IMG_PATH + (ghostNum % 4) + ".png", pos); //mod 4 
        moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1); // generating random 1-4 num for start of movement
    }

    public void setSpeed(int xspeed, int yspeed) {
        this.xspeed = xspeed;
        this.yspeed = yspeed;
    }
    @Override
    public void update() {
        switch (moveGhost) {
            case 1: // left down
                xspeed = -1;
                yspeed = 1;
                break;
            case 2: // right down
                xspeed = 1;
                yspeed = 1;
                break;
            case 3: // left up
                yspeed = -1;
                xspeed = -1;
                break;
            case 4: // right up
                xspeed = 1;
                yspeed = -1;
                break;
            default:
                break;
        }
            // border collision
            
            // check if x position in next frame is inside box

            // box col=lision on all side
            
        this.setTranslateX(this.getTranslateX() + xspeed);
        this.setTranslateY(this.getTranslateY() + yspeed);

    }

}