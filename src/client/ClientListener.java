package client;

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
    private boolean exitLoop = false;
    private ControllerLobby cLobby;

    public ClientListener(String ipAddress, ControllerLobby c) {
        this.address = ipAddress;
        this.cLobby = c;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, 1234);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (!exitLoop) {
                String name = cLobby.getName();
                oos.writeUTF(name);
                oos.flush();
                String name2 = ois.readUTF();
                Platform.runLater(() -> {
                    cLobby.displayName(name2);
                });
                exitLoop = true;
                oos.writeUTF(name);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
