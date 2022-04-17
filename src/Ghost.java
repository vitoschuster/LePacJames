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

    private int speed = 1;
    private int posX;
    private int posY;
    private ImageView ghostView;
    private int moveGhost;
    private boolean ifCollision = false;
    private int curX = 0;
    private int curY = 0;
    private char collionM = 'C';
    private PixelReader pixelReader = null;
    private int sizeWG = 0;
    private int sizeHG = 0;
    private Object lock = new Object();
    private int angleX = 0;
    private int angleY = 0;
    private int backgroundH = 0;
    private int backgroundW = 0;

    public Ghost(int posX, int posY, ImageView ghostView) {
        // saving data
        this.posX = posX;
        this.posY = posY;
        this.ghostView = ghostView;
        moveGhost = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        /// setting spawn location and adding to root
        this.ghostView.setTranslateX(this.posX);
        this.ghostView.setTranslateY(this.posY);
        this.getChildren().add(this.ghostView);
    }

    public void doOpen(Image background, Image ghost) {
        System.out.println(background.getWidth() + " " + background.getHeight());
        pixelReader = background.getPixelReader();
        backgroundW = (int) background.getWidth();
        backgroundH = (int) background.getHeight();
        sizeWG = (int) ghost.getWidth();
        sizeHG = (int) ghost.getHeight();
    }

    public void update() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switch (moveGhost) {
                    case 1:
                        posX -= speed;
                        posY += angleY;
                        ghostView.setTranslateX(posX);
                        ghostView.setTranslateY(posY);
                        break;
                    case 2:
                        posX += speed;
                        posY -= angleY;
                        ghostView.setTranslateX(posX);
                        ghostView.setTranslateY(posY);
                        break;
                    case 3:
                        posY -= speed;
                        posX += angleX;
                        ghostView.setTranslateX(posX);
                        ghostView.setTranslateY(posY);
                        break;
                    case 4:
                        posY += speed;
                        posX -= angleX;
                        ghostView.setTranslateX(posX);
                        ghostView.setTranslateY(posY);
                        break;
                }
                checkCollision();
            }
        });
    }

    public void checkCollision() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                curX = sizeWG + posX;
                curY = sizeHG + posY;
                // loop
                if (pixelReader.getColor(posX, posY).equals(Color.RED)
                        || pixelReader.getColor(curX, posY).equals(Color.RED)
                        || pixelReader.getColor(posX, curY).equals(Color.RED)
                        || pixelReader.getColor(curX, curY).equals(Color.RED)) {
                    speed = speed * -1;
                    if (moveGhost == 1 || moveGhost == 2) {
                        angleY = 1;
                    }
                    if (moveGhost == 3 || moveGhost == 4) {
                        angleX = 1;
                    }

                }
            }
        });
    }
}