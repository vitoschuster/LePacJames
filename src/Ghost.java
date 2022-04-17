
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
    private int xspeed = 3;
    private int yspeed = 3;
    private int x;
    private int y;
    private int curX = 0;
    private int curY = 0;
    private int widthG;
    private int heightG;
    private int angleX = 0;
    private int angleY = 0;
    private int moveGhost;
    private boolean ifCollision = false;
    private char collionM = 'C';
    private ImageView ghostView;
    private PixelReader pixelReader = null;

    private Object lock = new Object();

    private int widthB;
    private int heightB; 
   

    public Ghost(int x, int y, ImageView ghostView) {
        // saving data
        this.x = x;
        this.y = y;
        this.ghostView = ghostView;
        moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        /// setting spawn location and adding to root
        this.ghostView.setTranslateX(this.x);
        this.ghostView.setTranslateY(this.y);
        this.getChildren().add(this.ghostView);
    }

    public void doOpen(Image background, Image ghost) {
        System.out.println(background.getWidth() + " " + background.getHeight());

        pixelReader = background.getPixelReader();

        widthB = (int) background.getWidth() - 25;
        heightB = (int) background.getHeight() - 20;

        widthG = (int) ghost.getWidth();
        heightG = (int) ghost.getHeight();
    }

    public void update() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (moveGhost) {
                    case 1: // left down
                        x -= xspeed;
                        y += yspeed;
                        break;
                    case 2: // right down
                        x += xspeed;
                        y += yspeed;
                        break;
                    case 3: //left up
                        y -= yspeed;
                        x -= xspeed;
                        break;
                    case 4: //right up
                        x += xspeed;
                        y -= yspeed;
                        break;
                }
                ghostView.setTranslateX(x);
                ghostView.setTranslateY(y);

                checkCollision();
            }
        });
    }

    public void checkCollision() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
              
                if (x + widthG > widthB || x < 35) {
                    xspeed = -xspeed;
                }
                if (y + heightG > heightB || y < 30) {
                    yspeed = -yspeed;
                }


                int newX = x + xspeed;
                int newY = y + yspeed;

                //check if x position in next frame is inside box
                if (pixelReader.getColor(newX + widthG, y + heightG).equals(Color.RED)) {
                    xspeed = -xspeed;
                }
            }
        });
    }
}