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
    private Parent roo1t;
    private static final int W = 1120;
    private static final int H = 700;
    
    public void switchToGame(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        game = new Game(new Court(stage));
        stage.setTitle("LePac James");
        stage.setScene(new Scene(game, W, H));
        stage.show();
    }

    @FXML
    public void connectToServer(ActionEvent event) throws Exception {
        try {

            Socket socket = new Socket(tfIpAddress.getText(), 1234);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            name = tfName.getText();
            oos.writeObject(tfName.getText());
            oos.flush();
            FXMLLoader loaderLobby = new FXMLLoader(getClass().getResource("../fxml/menuwaitinglobby.fxml"));
            roo1t = loaderLobby.load();
    
            ControllerLobby controllerLobby = loaderLobby.getController();
    
            controllerLobby.displayName(name);
    
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(roo1t, W, H));
            stage.show();
            ClientListener cl=new ClientListener(socket,ois,oos,controllerLobby);
            cl.start();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
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
