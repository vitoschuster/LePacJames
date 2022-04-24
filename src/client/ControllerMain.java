package client;

import static client.Constants.*;
import client.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.*;

public class ControllerMain {

    @FXML
    private TextField tfName, tfIpAddress;

    private Stage stage;
    private Game game;
    private String name;
    private String address;
    private Parent lobbyPane;
    private ControllerLobby controllerLobby;
    private static final int W = 1120;
    private static final int H = 700;
    private boolean exitLoop = false;

    public void switchToGame(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        game = new Game(new Court(stage));
        stage.setTitle("LePac James");
        stage.setScene(new Scene(game, W, H));
        stage.show();
    }

    @FXML
    public void connectToServer(ActionEvent event) throws Exception {
        address = tfIpAddress.getText();
        name = tfName.getText();
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("../fxml/menuwaitinglobby.fxml"));
        lobbyPane = loaderLobby.load();
        controllerLobby = loaderLobby.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(lobbyPane, W, H));
        stage.show();
        ClientListener cl = new ClientListener(address, name, controllerLobby);
        cl.start();
    }

  
    public void switchToMultiplayer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menumultiplayer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W, H));
        stage.show();
    }

    public void switchToSettings(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menusettings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W, H));
        stage.show();
    }

    public void switchToMain(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menuscreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W, H));
        stage.show();
    }

}
