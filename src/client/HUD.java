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

public class HUD extends Pane {

    private TextField tfScore = new TextField("Score: 0");
    private TextField tfLives = new TextField("Lives: 3");
    private Scene scene;
    private int score = 0;

    public HUD(Scene scene) {
        this.scene = scene;
        this.createHUD();
    }

    public void createHUD() {
        int padding = 80;
        tfScore.resizeRelocate(scene.getWidth() - padding, 0, 80, 25);
        tfScore.setEditable(false);
        tfScore.setFocusTraversable(false);
    
        tfLives.resizeRelocate(scene.getWidth() - padding - 100, 0, 100, 25);
        tfLives.setEditable(false);
        tfLives.setFocusTraversable(false);
        this.getChildren().addAll(tfLives, tfScore);
        System.out.println(tfScore.getTranslateX() + " " + tfScore.getTranslateY());
    }
    
    public void update(int score) {
        this.score = score;
        Platform.runLater(() -> tfScore.setText("Score: " + this.score));
    }
}

