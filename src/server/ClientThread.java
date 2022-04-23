package server;

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

public class ClientThread extends Thread {
    private Socket cSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public List<ObjectOutputStream> clients = new ArrayList<>();

    public ClientThread(Socket cSocket) {
        this.cSocket = cSocket;
    }


    @Override
    public void run() {
        
        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            System.out.println("Client connection");
            clients.add(this.oos);
            while (true) {
                Object obj = ois.readObject();
                String message = (String) obj;
                System.out.println("User name: " + message);
                this.oos.writeObject(message);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
      
    }
}
