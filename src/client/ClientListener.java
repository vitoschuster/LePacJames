package client;

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
import client.ControllerLobby;
public class ClientListener extends Thread implements Runnable{
    private ObjectInputStream oos;
    private ObjectOutputStream ois;
    private Socket socket;
    private ControllerLobby ControllerLobby;
    public ClientListener(Socket socket,ObjectInputStream oos,ObjectOutputStream ois, ControllerLobby cl){
        this.socket=socket;
        this.oos=oos;
        this.ois=ois;
        this.ControllerLobby=cl;
    }
    @Override
    public void run(){
        try {
            String name2=(String) oos.readObject();
            ControllerLobby.displayName(name2);
            
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
