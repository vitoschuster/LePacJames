/**
 * ControllerLobby - Controller class used for switching to multiplayer fxml
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static client.Constants.*;

public class ControllerLobby {

    @FXML
    Button btnReady;

    @FXML
    TextArea taReady;

    Stage stage;
    int numPlayers = -1;
    int readyCounter = -1;

    /**
     * Sets name in the lobby TextArea.
     * 
     * @param name name of user
     */
    public void displayName(String name) {
        taReady.appendText(name + "\n");
        numPlayers++;
    }

    /**
     * Returns number of players.
     * 
     * @return number of players
     */
    public int getNumPlayers() {
        return numPlayers;
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
     * Method called when client presses btnReady
     * 
     * @param event ActionEvent
     */
    public void play(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        btnReady.setDisable(true);
        /** when other button is clicked */
    }

}
