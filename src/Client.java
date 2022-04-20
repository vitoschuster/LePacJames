import client.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;

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
        Scene scene = new Scene(game);
        stage.setScene(scene);
        
    }

    
}
