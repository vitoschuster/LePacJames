import client.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class Client extends Application {
    
    public Stage stage;
    public Game game;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent menuscreen = FXMLLoader.load(getClass().getResource("fxml/menuscreen.fxml"));
        stage.setTitle("LePac James Client");
        stage.setScene(new Scene(menuscreen, 1120, 700));
        stage.show();
    }

    
    
}
