import client.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class Controller {

    private Stage stage;
    private Game game;
    private static final int W = 1120;
    private static final int H = 700;
    
    public void switchToGame(ActionEvent event) throws Exception {
        stage = new Stage();
        game = new Game(new Court(stage));
        stage.setTitle("LePac James");
        stage.setScene(new Scene(game, W, H));
        stage.setOnCloseRequest(req -> System.exit(0));
        stage.show();
    }


    public void switchToSettings(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/menusettings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W, H));
        stage.show();
    }
    public void switchToMain(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/menuscreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W, H));
        stage.show();
    }


}
