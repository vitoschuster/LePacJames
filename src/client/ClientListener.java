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

public class ClientListener extends Thread{
    private ObjectInputStream oos;
    private ObjectOutputStream ois;
    private Socket socket;
    private ControllerLobby controller;

    public ClientListener(Socket socket, ObjectInputStream oos, ObjectOutputStream ois, ControllerLobby c) {
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
        this.controller = c;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String name = oos.readUTF();
                Platform.runLater(() -> controller.displayName(name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
