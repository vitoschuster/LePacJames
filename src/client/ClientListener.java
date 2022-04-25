package client;

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
    private ControllerLobby lobby;
    private TreeSet<String> clientNames = new TreeSet<>();
    private int id = 0;
    private int k = 0;
    private boolean isReady = false;

    public ClientListener(String ipAddress, String name, ControllerLobby c) {
        this.address = ipAddress;
        this.lobby = c;
        // this.readyCounter = this.lobby.readyCounter;
        this.clientNames.add(name);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, PORT.toInt());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            String myName = this.clientNames.iterator().next();
            lobby.displayName(myName);

            Platform.runLater(() -> {
                while (k < 1) { // lobby loop

                    try {
                        oos.writeObject("CONNECT:" + myName);

                        oos.flush();

                        String anotherName = ois.readUTF();
                        System.out.println(anotherName + " " + myName);
                        if (!anotherName.equals(myName)) {
                            lobby.displayName(anotherName);
                            k++;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // oos.writeObject("BTNCLICK:" + );
            // oos.flush();

            // String readyMessage =(String) ois.readObject();

            // while (!lobby.btnReady.isDisabled()) { //game loop

            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
