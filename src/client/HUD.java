package client;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class HUD extends Pane {

    private TextField tfScore = new TextField("0");
    private TextField tfLives = new TextField("3");
    public TextArea taChat = new TextArea("Chat: \n\n");
    public TextArea taChat2 = new TextArea("Chat: \n\n");
    public TextField tfMessage = new TextField();
    public TextField tfMessage2 = new TextField();
    public Button btnSend = new Button("Send");
    public Button btnSend2 = new Button("Send");
    private Button btnChat = new Button("Chat");
    private Scene scene;
    private int score = 0;
    public int lives = 2;
    public boolean isCoop;
    
    public HUD(Scene scene,boolean isCoop) {
        this.isCoop=isCoop;
        this.scene = scene;
        this.createHUD();
        btnChat.setOnAction(evt -> {
            Stage stage = new Stage();
            Stage stage2 = new Stage();
            Scene sceneChat = new Scene(new VBox(this.taChat, new FlowPane(this.tfMessage, this.btnSend)),
                    taChat.getPrefWidth(), taChat.getPrefHeight() + 20);
            Scene sceneChat2 = new Scene(new VBox(this.taChat2, new FlowPane(this.tfMessage2, this.btnSend2)),
                    taChat.getPrefWidth(), taChat.getPrefHeight() + 21);
            stage.setScene(sceneChat);
            stage2.setScene(sceneChat2);
            stage2.setX(scene.getWidth() + stage.getWidth());
            stage2.show();
            stage.setX(scene.getWidth() + stage.getWidth());
            stage.show();
        });
    }

    /**
     * Creates a new instance of the HUD (buttons and fields around the screen)
     */
    public void createHUD() {
        Font font = Font.loadFont("file:NBA Lakers.ttf", 14);
        tfScore.resizeRelocate(scene.getWidth()/2 - 49, 69, 35, 25);
        tfScore.setPrefWidth(35);
        tfScore.setEditable(false);
        tfScore.setFocusTraversable(false);
        tfScore.setBackground(Background.EMPTY);
        tfScore.setFont(font);
        

        tfLives.resizeRelocate(scene.getWidth()/2 + 23.5, 69, 30, 25);
        tfLives.setPrefWidth(30);
        tfLives.setEditable(false);
        tfLives.setFocusTraversable(false);
        tfLives.setBackground(Background.EMPTY);
        tfLives.setFont(font);

        btnChat.resizeRelocate(2, 0, 70, 25);
        btnChat.setFocusTraversable(false);
        taChat.setEditable(false);
        taChat.setWrapText(true);
        taChat.setPrefSize(180, 380);
        tfMessage.setPrefWidth(100);
        taChat2.setEditable(false);
        taChat2.setWrapText(true);
        taChat2.setPrefSize(180, 380);
        tfMessage2.setPrefWidth(100);
        if(!isCoop){
            this.getChildren().addAll(tfLives, tfScore);
        }else{
            this.getChildren().addAll(tfLives,tfScore, btnChat);
        }
    }

    /**
     * Updating the HUD with the progress of the game
     * Shows the number of lives and score of the players
     */
    public void update(int score, int lives) {
        this.score = score;
        Platform.runLater(() -> {
            tfScore.setText(this.score + "");
            if(!isCoop){
                tfLives.setText(this.lives + "");
            }
        });
    }

}
