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
    TextArea taReady;
    Stage stage;

    public void displayName(String name) {
        taReady.appendText(name+"\n");
    }
    public String getName(){
        return taReady.getText();
    }
    public void switchToMultiplayer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/menumultiplayer.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, W.toInt(), H.toInt()));
        stage.show();
    }
}
