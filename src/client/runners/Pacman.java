package client.runners;

import client.runners.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.io.*;
import java.security.*;
import javafx.util.Duration;
import java.util.*;
import java.util.concurrent.*;
//load images for every runner inside it
/**
 * Racer creates the race lane (Pane) and the ability to
 * keep itself going (Runnable)
 */
public class Pacman extends Runner {
    private char collionM = 'R';
    // private int xw = 0; //
    // private int yh = 0; //
    // private static final int SPEED = 4;
    // private static final int REFRESH_RATE = 1000 / 60;

    // private List<Image> images = new ArrayList<>();
    // private List<Timeline> timelines = new ArrayList<>();
    // private List<ImageView> imageViews = new ArrayList<>(); // arrayList of icon views - used to cycle the  // animation
    // private Group pacmanGroup;
    private Point point;
    private TimerTask timerTaskMover;
    private Timer timerMover = new Timer();
    private boolean goingForward = false;

    private static final String IMG_PATH = "img/lepac.gif";

    // load image and get pixel position

    public Pacman() {
        super(IMG_PATH);
    }

   

    /**
     * update() method keeps the thread (racer) alive and moving.
     */
    public void update() {
        
        // checkCollision();
        this.setTranslateX(pos.getX()); 
        this.setTranslateY(pos.getY());
        this.setRotate(angle);
        // timelines.get(0).play(); // play the animation
        
        /*
         * if (racePosX > 800)
         * racePosX = 1;
         * if (racePosY > 500)
         * racePosY = 1;
         */
        // ogranicenje kretanja s obzirom na pixel
        // full collion

        // offset of the picture - the center needs to be the cneter of

    } // end update()

    public void checkCollision() {
        // // get pixel reader

        // PixelReader pixelReader = bgProps.getPixelReader();
        // xw = (int) images.get(0).getWidth() + x;
        // yh = (int) images.get(0).getHeight() + y;

        // // player collision
        // // loop
        // switch (collionM) {
        //     case 'd':
        //         if (pixelReader.getColor(xw, yh).equals(Color.RED)
        //                 || pixelReader.getColor(xw, y).equals(Color.RED))
        //             x -= SPEED;
        //         break;
        //     case 's':
        //         if (pixelReader.getColor(x, yh).equals(Color.RED)
        //                 || pixelReader.getColor(xw, yh).equals(Color.RED))
        //             y -= SPEED;
        //         break;
        //     case 'w':
        //         if (pixelReader.getColor(x, y).equals(Color.RED)
        //                 || pixelReader.getColor(xw, y).equals(Color.RED))
        //             y += SPEED;
        //         break;
        //     case 'a':
        //         if (pixelReader.getColor(x, y).equals(Color.RED)
        //                 || pixelReader.getColor(x, yh).equals(Color.RED))
        //             x += SPEED;
        //         break;
        // }

        // // player vs ghost collision
        // for (int i = 0; i < ghosts.size(); i++) {
        //     if (x < (ghosts.get(i).getX() + ghosts.get(i).getW()) && xw > ghosts.get(i).getX()
        //             && y < ghosts.get(i).getY() + ghosts.get(i).getH() && yh > ghosts.get(i).getY()) {
        //         if (k == 0) {
        //             String path = "./vid/lose.mp4";
        //             Media media = new Media(new File(path).toURI().toString());
        //             MediaPlayer player = new MediaPlayer(media);
        //             MediaView mediaView = new MediaView(player);
        //             root.getChildren().add(mediaView);
        //             player.play();
        //             player.setOnEndOfMedia(() -> restart(stage));
        //         }
        //         k++;

        //     }
        // }

        // cant use enhanced foreach for deleting
        // copy to another arraylist and remove all elements
        // subtract the two arrays or use Iterator
    }

    public boolean checkCollisionWithGhost(Ghost ghost) {
        return true;
    }
    

    public void move(boolean isMoving, char movement) {
        // if (isMoving && !goingForward) {
        //     timerTaskMover = new TimerTask() {
        //         @Override
        //         public void run() {
        //             synchronized (timerMover) {
        //                 if (movement == 'w') {
        //                      -= SPEED;
        //                     angle = 270;
        //                     pacmanGroup.setScaleY(1);
        //                     collionM = 'w';
        //                 } else if (movement == 'a') {
        //                     x -= SPEED;
        //                     angle = 180;
        //                     collionM = 'a';
        //                     pacmanGroup.setScaleY(-1);
        //                 } else if (movement == 's') {
        //                     y += SPEED;
        //                     angle = 90;
        //                     pacmanGroup.setScaleY(-1);
        //                     collionM = 's';
        //                 } else if (movement == 'd') {
        //                     x += SPEED;
        //                     angle = 0;
        //                     pacmanGroup.setScaleY(1);
        //                     collionM = 'd';
        //                 }
        //             }
        //         }
        //     };
        //     timerMover.scheduleAtFixedRate(timerTaskMover, 0, REFRESH_RATE);
        //     goingForward = true;
        // } else if (!isMoving && goingForward) {
        //     timerTaskMover.cancel();
        //     goingForward = false;
        // }
    }
} // end inner class Racer