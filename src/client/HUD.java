package client;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class HUD extends Pane {

    private TextField tfScore = new TextField("Score: 0");
    private TextField tfLives = new TextField("Lives: 3");
    public TextArea taChat = new TextArea("Chat: \n");
    public TextField tfMessage = new TextField();
    public Button btnSend = new Button("Send");
    private Button btnChat = new Button("Chat");
    private Scene scene;
    private Stage stage;
    private int score = 0;
    public int lives = 2;
    public boolean isCoop;
    
    public HUD(Scene scene,boolean isCoop) {
        this.isCoop=isCoop;
        this.scene = scene;
        this.createHUD();
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
        int padding = 80;
        tfScore.resizeRelocate(scene.getWidth() - padding, 0, 80, 25);
        tfScore.setEditable(false);
        tfScore.setFocusTraversable(false);

        tfLives.resizeRelocate(scene.getWidth() - padding - 100, 0, 100, 25);
        tfLives.setEditable(false);
        tfLives.setFocusTraversable(false);

        btnChat.resizeRelocate(scene.getWidth() - padding - 100, 0, 70, 25);
        btnChat.setFocusTraversable(false);
        taChat.setEditable(false);
        taChat.setWrapText(true);
        taChat.setPrefSize(180, 480);
        tfMessage.setPrefWidth(80);
        if(!isCoop){
            this.getChildren().addAll(tfLives, tfScore);
        }else{
            this.getChildren().addAll(tfScore, btnChat);
        }
    }

    /**
     * Updating the HUD with the progress of the game
     * Shows the number of lives and score of the players
     */
    public void update(int score, int lives) {
        this.score = score;
        Platform.runLater(() -> {
            tfScore.setText("Score: " + this.score);
            if(!isCoop){
                tfLives.setText("Lives: " + this.lives);
            }
        });
    }

}
