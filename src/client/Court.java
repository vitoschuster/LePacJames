package client;

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

public class Court extends Pane {
    // arrayliste
    public Stage stage;
    public Image image;
    public ImageView imageView;
    private PixelReader reader;
    

    private List<List<Ball>> balls = new ArrayList<>();
    // private int gridWidth = 5;
    // private int gridHeight = 5;
    // private int score = 0;
    // private int endBall = 0;

    private static final String PATH_BG = "img/basketball_court_props.png";
    private static final String PATH_BG_PROPS = "img/bgProps.png"; // bg for collision

    public Court(Stage stage) {
        this.stage = stage;

        try { // loading images (real and fake bg)
            this.image = new Image(new FileInputStream(PATH_BG_PROPS));
            this.imageView = new ImageView(new Image(new FileInputStream(PATH_BG)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        this.getChildren().add(this.imageView);
    }


    public boolean isCollisionMap(double xpos, double ypos, double width, double height, double deg) {
        reader = this.image.getPixelReader();
        final int pad = 3;
        int x = (int) xpos;
        int y = (int) ypos;
        int xw = (int) (width + x);
        int yh = (int) (height + y);
        int angle = (int) deg;

       
        switch (angle) { //pacman
            case 0:
                if (reader.getColor(xw + pad, yh).equals(Color.RED)
                        || reader.getColor(xw + pad, y).equals(Color.RED))
                    return true;
                break;
            case 90:
                if (reader.getColor(x, yh + pad).equals(Color.RED)
                        || reader.getColor(xw, yh + pad).equals(Color.RED))
                    return true;
                break;
            case 270:
                if (reader.getColor(x, y - pad).equals(Color.RED)
                        || reader.getColor(xw, y - pad).equals(Color.RED))
                    return true;
                break;
            case 180:
                if (reader.getColor(x - pad, y).equals(Color.RED)
                        || reader.getColor(x - pad, yh).equals(Color.RED))
                    return true;
                break;
            
        }


        return false;
    }
    
    public void handleCollision(Ghost g) {
        switch(g.moveGhost){
            case 1:
            if (reader.getColor( - 3, yh).equals(Color.RED)
                    || reader.getColor(xw, yh).equals(Color.RED)) {
                angle = 3;
                return true;
            } else if (reader.getColor(x, y).equals(Color.RED)
                    || reader.getColor(x, yh + 3).equals(Color.RED)) {
                angle = 2;
                return true;
            }
            break;
        case 2:
            if (reader.getColor(x, y).equals(Color.RED)
                    || reader.getColor(x + 3,yh).equals(Color.RED)) {
                angle = 1;
                return true;
            } else if (reader.getColor(x, yh +3).equals(Color.RED)
                    || reader.getColor(xw, yh).equals(Color.RED)) {
                angle = 4;
                return true;
            }
            break;
        case 3:
            if (reader.getColor(xw, y).equals(Color.RED)
                    || reader.getColor(x, y-3).equals(Color.RED)) {
                angle = 1;
                return true;
            } else if (reader.getColor(x-3, y).equals(Color.RED)
                    || reader.getColor(x, yh).equals(Color.RED)) {
                angle = 4;
                return true;
            }
            break;
        case 4:
            if (reader.getColor(x, y).equals(Color.RED)
                    || reader.getColor(xw+3, y).equals(Color.RED)) {
                angle = 3;
                return true;
            } else if (reader.getColor(xw, y-3).equals(Color.RED)
                    || reader.getColor(xw, yh).equals(Color.RED)) {
                angle = 2;
                return true;
            }
            break;
    }
}
}
