
/**
 * Server.java - a simple GUI server implementation that runs the server and ServerThread to handle client connections  
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */

import server.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Server extends Application {
    private Scene scene;
    public TextArea taList = new TextArea();
    private Button btnClear = new Button("Clear");

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    /** 
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        taList.setPrefHeight(200);
        scene = new Scene(new VBox(btnClear, taList), 250, 200);
        btnClear.setOnAction(evt -> {
            taList.clear();
        });
        stage.setScene(scene);
        stage.show();
        new ServerThread(taList).start();
    }
}
