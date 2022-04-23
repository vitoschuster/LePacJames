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

public class ServerThread extends Thread  {
    private static final int SERVER_PORT = 1234;
    public static List<ObjectOutputStream> clients = new ArrayList<>();

    @Override
    public void run() {
        try(ServerSocket sSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Opening SOCKET PORT");
            while (true) {
                System.out.println("Waiting client to connect");
                Socket cSocket = sSocket.accept();
                ClientThread ct = new ClientThread(cSocket);
                ct.start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
    }
}
