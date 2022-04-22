/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */

package client.runners;
import client.*;
import javafx.geometry.*;
import java.util.concurrent.*;

public class Ghost extends Runner {
    // ghost location

    public int moveGhost;
    public static final int GHOST_SPEED = 1;
    private Court court;
    public static final String IMG_PATH = "img/ghost";

    public Ghost(Court court, int ghostNum, Point2D pos) {
        super(IMG_PATH + (ghostNum % 4) + ".png", pos); //mod 4 
        moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1); //  random 1-4 num for start of movement
        this.court = court;
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
        this.pos = new Point2D(this.getTranslateX(), this.getTranslateY());
        this.setTranslateX(this.getTranslateX() + xspeed);
        this.setTranslateY(this.getTranslateY() + yspeed);

    }

}