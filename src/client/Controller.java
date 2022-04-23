package client;

import javax.swing.Action;

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

public class Controller {

    private Stage stage;
    private Game game;
    private static final int W = 1120;
    private static final int H = 700;
    
    public void switchToGame(ActionEvent event) throws Exception {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        game = new Game(new Court(stage));
        stage.setTitle("LePac James");
        stage.setScene(new Scene(game, W, H));
        stage.show();
    }
    public void connectToServer(ActionEvent event)throws Exception{
        try {
            Socket socket = new Socket("", 1234);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
   
            oos.writeObject("luka");
            oos.flush();
           
   
            
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
