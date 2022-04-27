package client.runners;

import client.*;
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



public abstract class Runner extends ImageView implements Serializable {

    private static final long serialVersionUID = 1L;
    public Point2D pos = new Point2D(0, 0);
    public Image image;
    public double xspeed = 0;
    public double yspeed=0;
    public double angle = 0;
    public int score = 0;
    public int lives = 2;
    public int height;
    public int width;

    protected Runner() {}

    protected Runner(String imagePath, Point2D pos) {
        this.setImage(this.loadImage(imagePath));
        this.setTranslateX(pos.getX());
        this.setTranslateY(pos.getY());
        this.height = (int) this.getImage().getHeight();
        this.width = (int) this.getImage().getWidth();
    }

    public Image loadImage(String path) {

        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    /**
     * Method that is called in the animation timer 
     * Code that needs to be constantly updated goes here
     */
    public abstract void update();

    public abstract boolean isMoving(double lastX, double lastY);
    
}
