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
    private String name;
    private List<String> names = new ArrayList<>();
    private boolean first = true;
    private boolean second = true;

    public ClientListener(String ipAddress, String name, ControllerLobby c) {
        this.address = ipAddress;
        this.cLobby = c;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, 1234);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            while (true) {
                oos.writeUTF("NAME:" + name);
                oos.flush();
                int length = ois.readInt();
                System.out.println("LENGTH " + length);
                for (int i = 0; i < length; i++) {
                    String name2 = ois.readUTF();
                    Platform.runLater(() -> {
                        cLobby.displayName(name2);
                    });

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
