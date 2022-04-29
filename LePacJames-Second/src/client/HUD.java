package client;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class HUD extends Pane {

    public TextField tfScore = new TextField("0");
    public TextField tfLives = new TextField("2");
    public TextArea taChat = new TextArea("Chat: \n");
    public TextField tfMessage = new TextField();
    public Button btnSend = new Button("Send");
    private Button btnChat = new Button("Chat");
    private Scene scene;
    private Stage stage;
    private int score = 0;
    public int lives = 2;
    public boolean isCoop;

    public HUD(Scene scene, boolean isCoop) {
        this.isCoop = isCoop;
        this.scene = scene;
        this.createHUD();
        if(this.isCoop){
            this.tfLives.setText("1");
            this.lives=1;
        }
        btnChat.setOnAction(evt -> {
            stage = new Stage();
            Scene sceneChat = new Scene(new VBox(this.taChat, new FlowPane(this.tfMessage, this.btnSend)),
                    taChat.getPrefWidth(), taChat.getPrefHeight() + 20);
            stage.setScene(sceneChat);
            stage.setX(scene.getWidth() + stage.getWidth());
            stage.show();
        });
    }

    /**
     * Creates a new instance of the HUD (buttons and fields around the screen)
     */
    public void createHUD() {
        Font font = Font.loadFont("file:NBA Lakers.ttf", 14);
        int padding = 80;
        tfScore.resizeRelocate(scene.getWidth() / 2 - 49, 69, 35, 25);
        tfScore.setPrefWidth(35);
        tfScore.setEditable(false);
        tfScore.setFocusTraversable(false);
        tfScore.setBackground(Background.EMPTY);
        tfScore.setFont(font);

        tfLives.resizeRelocate(scene.getWidth() / 2 + 23.5, 69, 30, 25);
        tfLives.setPrefWidth(30);
        tfLives.setEditable(false);
        tfLives.setFocusTraversable(false);
        tfLives.setBackground(Background.EMPTY);
        tfLives.setFont(font);

        btnChat.resizeRelocate(scene.getWidth() - padding - 100, 0, 70, 25);
        btnChat.setFocusTraversable(false);
        taChat.setEditable(false);
        taChat.setWrapText(true);
        taChat.setPrefSize(180, 480);
        tfMessage.setPrefWidth(80);

        this.getChildren().addAll(tfLives, tfScore);

    }

    /**
     * Updating the HUD with the progress of the game
     * Shows the number of lives and score of the players
     */
    public void update(int score, int lives) {
        this.score = score;
        Platform.runLater(() -> {
            tfScore.setText(this.score + "");
            if (!isCoop) {
                tfLives.setText(this.lives + "");
            }
        });
    }

}
