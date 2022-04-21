import client.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class Client extends Application {

    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("LePac James Client");
        //menus
        //fxml

        
        Game game = new Game(new Court(stage));
        stage.setScene(new Scene(game,1120, 700));
        stage.show();
    }

    
}
