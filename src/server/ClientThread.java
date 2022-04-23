package server;

import java.io.*;
import static server.ServerThread.clients;
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
   

    public ClientThread(Socket cSocket) {
        this.cSocket = cSocket;
    }


    @Override
    public void run() {
        
        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            System.out.println("Client connection");
            //list of clients need to be on the server
            clients.add(this.oos);
            while (true) {
                String message = ois.readUTF();
                System.out.println("User name: " + message);

                for (ObjectOutputStream stream : clients) {
                    if (!stream.equals(this.oos)) {
                        stream.writeUTF(message);
                        stream.flush();
                        System.out.println("name flushed");
                    }
                }
                
            }
        } catch (EOFException e) {
            System.out.println("Client disconnected");
            e.printStackTrace();
        } catch (IOException e) {e.printStackTrace();}
      
    }
}
