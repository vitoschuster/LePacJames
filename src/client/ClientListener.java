package client;

import static server.Network.*;
import static client.Constants.*;
import client.*;
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

public class ClientListener extends Thread {
    private String address;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;
    private ControllerLobby cLobby;
    private TreeSet<String> clientNames = new TreeSet<>();
    private int id = 0;

    private int k  = 0;

    public ClientListener(String ipAddress, String name, ControllerLobby c) {
        this.address = ipAddress;
        this.cLobby = c;
        this.clientNames.add(name);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, PORT.toInt());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
          
            String myName = this.clientNames.iterator().next();
            cLobby.displayName(myName);

            while (k < 1) { //lobby loop
                oos.writeObject("CONNECT:" + myName);
                oos.flush();
                String anotherName = ois.readUTF();
                if (!anotherName.equals(myName)) {
                    cLobby.displayName(anotherName);
                    k++;
                }
            }
            
            while (true) { //game loop
                
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
