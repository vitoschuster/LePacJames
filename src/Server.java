import server.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

public class Server extends Application {
    private Stage stage;
    private Scene scene;
    private VBox root = null;
    public TextArea taList = new TextArea();
    private Button btnClear = new Button("Clear");

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) throws Exception {
        taList.setPrefHeight(200);
        scene = new Scene(new VBox(btnClear,taList), 250, 200);
        stage.setScene(scene);
        stage.show();
        new ServerThread().start();
    }
}
