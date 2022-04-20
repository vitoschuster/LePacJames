package client.runners;

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

public abstract class Runner extends ImageView {
    public Point2D pos = new Point2D(0, 0);
    public Image image;
    public double speed = 0;
    public double angle = 0;
    public double score = 0;
    public int height;
    public int width;

    protected Runner(String imagePath) {
        this.setImage(this.loadImage(imagePath));
        // this.setScaleX(-1)
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

    public void timer() {

    }

    public abstract void update();
}