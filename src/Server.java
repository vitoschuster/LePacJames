import server.*;
import java.io.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Application {
    private Stage stage;
    private Scene scene;
    private VBox root = null;
    public TextArea taList = new TextArea();
    private Button btnClear = new Button("Clear");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = new VBox();
        root.getChildren().addAll(btnClear, taList);
        root.setAlignment(Pos.CENTER);
        taList.setPrefHeight(200);
        scene = new Scene(root, 250, 200);
        stage.setScene(scene);
        stage.show();
        ServerThread st = new ServerThread();
        st.start();
    }
}
