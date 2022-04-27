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
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class HUD extends Pane {

    private TextField tfScore = new TextField("Score: 0");
    private TextField tfLives = new TextField("Lives: 3");
    private TextArea taChat = new TextArea("Chat: \n");
    private TextField tfMessage = new TextField();
    private Button btnSend = new Button("Send");
    private Button btnChat = new Button("Chat");
    private Scene scene;
    private Stage stage;
    private int score = 0;
    public int lives=2;

    public HUD(Scene scene) {
        this.scene = scene;
        this.createHUD();
        btnChat.setOnAction(evt -> {
            stage = new Stage();
            Scene sceneChat = new Scene(new VBox(this.taChat, new FlowPane(this.tfMessage,this.btnSend)),taChat.getPrefWidth(),taChat.getPrefHeight() + 20);
            stage.setScene(sceneChat);
            stage.setX(scene.getWidth() + stage.getWidth());
            stage.show();
        });
    }

    public void createHUD() {
        int padding = 80;
        tfScore.resizeRelocate(scene.getWidth() - padding, 0, 80, 25);
        tfScore.setEditable(false);
        tfScore.setFocusTraversable(false);

        tfLives.resizeRelocate(scene.getWidth() - padding - 100, 0, 100, 25);
        tfLives.setEditable(false);
        tfLives.setFocusTraversable(false);

        btnChat.resizeRelocate(scene.getWidth() - padding - 170, 0, 70, 25);
        btnChat.setFocusTraversable(false);
        taChat.setEditable(false);
        taChat.setWrapText(true);
        taChat.setPrefSize(180, 680);
        tfMessage.setPrefWidth(80);
    
        this.getChildren().addAll(tfLives, tfScore, btnChat);
        System.out.println(tfScore.getTranslateX() + " " + tfScore.getTranslateY());
    }
    
    public void update(int score,int lives) {
        this.score = score;
        Platform.runLater(() ->{
            tfScore.setText("Score: " + this.score);
            tfLives.setText("Lives: "+this.lives);
        });
    }

}
