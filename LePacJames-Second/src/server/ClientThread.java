package server;

/**
 * ClientThread - a separate Thread that is instantiated when the client makes a connection to the server
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
import static server.ServerThread.*;
import server.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Platform;

public class ClientThread extends Thread {
    private Socket cSocket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    private Integer id;
    private boolean first = true;

    /**
     * Main constructor for client thread
     * 
     * @param cSocket socket of the client
     * @param id      unique id of the client connected
     */
    public ClientThread(Socket cSocket, Integer id) {
        this.cSocket = cSocket;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            textArea.appendText("Client " + id + " connected\n");
            this.oos.writeInt(id);
            this.oos.flush();
            while (true) {
                Object obj = ois.readObject();
                if (obj instanceof String) {
                    String message = (String) obj;
                    String[] split = message.split(":");
                    switch (split[0]) {
                        case "CONNECT":
                            doLobby(split[1]);
                            break;
                        case "READY":
                            doReady(split[1]);
                            break;
                        case "CHAT":
                            doChat(split[1]);
                            break;
                        default:
                            break;
                    }
                } else if (obj instanceof Packet) {
                    Packet packet = (Packet) obj;
                    for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                        if (!entry.getKey().equals(packet.getId())) {
                            synchronized (oos) {
                                entry.getValue().oos.writeObject(packet);
                                entry.getValue().oos.flush();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            try {
                textArea.appendText("Multiplayer done/cancelled\n");
                cSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            textArea.appendText("Client " + id + " disconnected\n");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Synchronized method that handles all requests from clients in the waiting
     * lobby
     * Alerts the clients when all players are ready to play
     * 
     * @param readyConf
     */
    private synchronized void doReady(String readyConf) {
        try {
            if (!this.id.equals(0) && this.id.equals(clients.size() - 1)) {
                for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                    // entry.getValue().oos.reset();
                    textArea.appendText("Client ready\n");
                    entry.getValue().oos.writeUTF("everyone is ready");
                    entry.getValue().oos.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Synchronized method that appends the names of connected clients to the
     * waiting lobby text area
     * 
     * @param playerName
     */
    private synchronized void doLobby(String playerName) {
        try { // updating players in lobby - sending names
            for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                if (entry.getKey().equals(this.id)) {
                    this.oos.writeUTF(playerName);
                    this.oos.flush();
                } else {
                    entry.getValue().oos.writeUTF(playerName);
                    entry.getValue().oos.flush();
                }

            }
        }catch(NullPointerException npe){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Synchronized method used for handling chat message communication between the
     * clients
     * 
     * @param playerName
     */
    private synchronized void doChat(String playerName) {
        try { // updating players in lobby - sending names
            for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                if (entry.getKey().equals(this.id)) {
                    this.oos.writeUTF(playerName);
                    this.oos.flush();
                } else {
                    entry.getValue().oos.writeUTF(playerName);
                    entry.getValue().oos.flush();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}