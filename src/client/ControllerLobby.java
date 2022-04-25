package client;

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
import static client.Constants.*;

public class ControllerLobby {

    @FXML
    Button btnReady;

    @FXML
    TextArea taReady; 
    Stage stage;
    int numPlayers = -1;
    int readyCounter = -1;
     

    public void displayName(String name) {
        taReady.appendText(name + "\n");
        numPlayers++;
    }

    // public boolean isReady() {
    //TODO - buttons pressed (ready);     
    // }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void switchToMultiplayer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menumultiplayer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W.toInt(), H.toInt()));
        stage.show();
    }

    public void play(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        readyCounter++;
        btnReady.setDisable(true);

        /** when other button is clicked */
    }
    


}
