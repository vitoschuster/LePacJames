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

public class ServerThread extends Network {
    private static final int SERVER_PORT = 1234;
    public List<ObjectOutputStream> clients = Collections.synchronizedList(new ArrayList());

    @Override
    public void run() {
        try (ServerSocket sSocket = new ServerSocket(SERVER_PORT)) {
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

class ClientThread extends Network implements Runnable {
    private Socket cSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int k = 0;
    private Vector<ObjectOutputStream> clientsC = new Vector<>();

    public ClientThread(Socket cSocket) {
        this.cSocket = cSocket;
    }

    @Override
    public void run() {

        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            System.out.println("Client connection");
            synchronized (clientsA) {
                clientsA.add(this.oos);
            }
            System.out.println(clientsA.size());
            // list of clients need to be on the server
            while (true) {
                String message = ois.readUTF();
                String[] split = message.split(":");
                // System.out.println(clientsA.size());
                switch (split[0]) {
                    case "NAME": {
                        // System.out.println("User name: " + split[1]);
                        oos.writeInt(clientsA.size());
                        oos.flush();
                        for (ObjectOutputStream oos : clientsA) {
                            oos.writeUTF(split[1]);
                            oos.flush();
                        }
                        break;
                    }

                }

            }

        } catch (EOFException e) {
            System.out.println("Client disconnected");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
