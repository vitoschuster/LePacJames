/**
 * Ghost - Class that represents a ghost following a pacman
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
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

public class Ghost extends Pane {

    // ghost location
    private int xspeed = 1;
    private int yspeed = 1;
    private int x;
    private int y;
    private int widthG;
    private int heightG;
    private int widthB;
    private int heightB;
    private int moveGhost;
    private ImageView ghostView;
    private PixelReader pixelReader = null;
    private Object lock = new Object();


    public Ghost(int x, int y, ImageView ghostView) {
        // saving data
        this.x = x;
        this.y = y;
        this.ghostView = ghostView;
        moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1); //generating random 1-4 num for start of movement

        /// setting spawn location and adding to root
        this.ghostView.setTranslateX(this.x);
        this.ghostView.setTranslateY(this.y);
        this.getChildren().add(this.ghostView);
    }

    //getters and setters for coordinates and dimensions of ghost
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return heightG;
    }

    public int getW() {
        return widthG;
    }

    /**
     * Opening picture of ghost and background
     * @param background
     * @param ghost
     */
    public void doOpen(Image background, Image ghost) {
        System.out.println(background.getWidth() + " " + background.getHeight());
        pixelReader = background.getPixelReader(); // getting pixel reader from background
        widthB = (int) background.getWidth() - 30; // bg width
        heightB = (int) background.getHeight() - 25; // bg height
        widthG = (int) ghost.getWidth(); // ghost width
        heightG = (int) ghost.getHeight(); // ghost height
    }

    /**
     * Calling this method in game loop 
     * Starting the movement of each ghost based on random number chosen 1-4
     */
    public void update() {
        Platform.runLater(() -> {
            switch (moveGhost) {
                case 1: // left down
                    x -= xspeed;
                    y += yspeed;
                    break;
                case 2: // right down
                    x += xspeed;
                    y += yspeed;
                    break;
                case 3: // left up
                    y -= yspeed;
                    x -= xspeed;
                    break;
                case 4: // right up
                    x += xspeed;
                    y -= yspeed;
                    break;
                default:
                    break;
            }
            ghostView.setTranslateX(x);
            ghostView.setTranslateY(y);
            checkCollision(); //checking collision with borders and pacman
        });
    }

    public void checkCollision() {
        Platform.runLater(() -> {
            //border collision
            if (x + widthG > widthB || x < 35) {
                xspeed = -xspeed;
            }
            if (y + heightG > heightB || y < 30) {
                yspeed = -yspeed;
            }

            int newX = x + xspeed;
            int newY = y + yspeed;
            // check if x position in next frame is inside box
            // if (pixelReader.getColor(x + widthG, y + heightG).equals(Color.RED)
            //         || pixelReader.getColor(x, y + heightG).equals(Color.RED)
            //         || pixelReader.getColor(x, y).equals(Color.RED)
            //         || pixelReader.getColor(x + widthG, y).equals(Color.RED)) {
            //     xspeed = -xspeed; //changing speed direction on collision
            //     yspeed = -yspeed;
            // }

            //box col=lision on all side

            // if moving right
            if (xspeed > 0) { //check projected position to right 
                if (pixelReader.getColor(x + widthG + xspeed, y).equals(Color.RED)
                        || pixelReader.getColor(x + widthG + xspeed, y + heightG).equals(Color.RED)) {
                    xspeed = -xspeed;
                }
            }
            //if moving left
             if (xspeed < 0) { 
                if (pixelReader.getColor(x + xspeed, y).equals(Color.RED)
                        || pixelReader.getColor(x + xspeed, y + heightG).equals(Color.RED)) {
                    xspeed = -xspeed;
                }

            }
            
            //if moving down
             if (yspeed < 0) {
                if(pixelReader.getColor(x,y+yspeed).equals(Color.RED)
                    || pixelReader.getColor(x + widthG, y + yspeed).equals(Color.RED)) {
                yspeed = -yspeed;
                }

            // if moving up
            }  if (yspeed > 0) {
                if(pixelReader.getColor(x, y + heightG +yspeed).equals(Color.RED)
                        || pixelReader.getColor(x + widthG, y + heightG+yspeed).equals(Color.RED)) {
                        yspeed = -yspeed;
                    }
            }
                    
        });
    }
}