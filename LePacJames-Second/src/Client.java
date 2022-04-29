
/**
 * Client.java - a class that starts the application and loading to the main menu GUI
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */

import client.*;
import static client.Constants.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

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
        stage.setScene(new Scene(menuscreen, W.toInt(), H.toInt()));
        stage.show();
    }
}
