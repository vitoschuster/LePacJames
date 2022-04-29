/**
 * ControllerMain - Controller class used for switching throught fxml
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client;

import static client.Constants.*;


import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ControllerMain {

    @FXML
    private TextField tfName, tfIpAddress;

    private Stage stage;
    private Game game;
    private String name;
    private String address;
    private Parent lobbyPane;
    private ControllerLobby controllerLobby;

    /**
     * Switch to singleplayer when button for it pressed
     * 
     * @param event click of button
     */
    public void switchToGame(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        game = new Game(new Court(stage));
        stage.setTitle("LePac James");
        stage.setScene(new Scene(game, W.toInt(), H.toInt()));
        stage.show();
    }

    /**
     * Loads lobby and starts ClientListener
     * 
     * @param event click of button
     */
    @FXML
    public void connectToServer(ActionEvent event) throws Exception {
        address = tfIpAddress.getText();
        name = tfName.getText();
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("../fxml/menuwaitinglobby.fxml"));
        lobbyPane = loaderLobby.load();
        controllerLobby = loaderLobby.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(lobbyPane, W.toInt(), H.toInt()));
        stage.show();
        ClientListener cl = new ClientListener(address, name, controllerLobby);
        cl.start();
    }

    /**
     * Switches screen to multiplayer tab
     * 
     * @param event ActionEvent
     */
    public void switchToMultiplayer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menumultiplayer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W.toInt(), H.toInt()));
        stage.show();
    }

    /**
     * Switches screen to settings tab
     * 
     * @param event ActionEvent
     */
    public void switchToSettings(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menusettings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W.toInt(), H.toInt()));
        stage.show();
    }


    /**
     * Switches screen to main tab
     * 
     * @param event ActionEvent
     */
    public void switchToMain(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menuscreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W.toInt(), H.toInt()));
        stage.show();
    }

}
