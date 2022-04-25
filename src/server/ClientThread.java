package server;

import static server.ServerThread.*;
import java.io.*;
import java.io.ObjectInputStream.GetField;
import java.net.*;
import java.util.*;


public class ClientThread extends Thread {
    private Socket cSocket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    private Integer id;

    public ClientThread(Socket cSocket, Integer id) {
        this.cSocket = cSocket;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            System.out.println("Client connection");
            System.out.println(clients.size());
            while (true) {
                Object obj = ois.readObject();
                oos.reset();
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

                    }
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            System.out.println("Client disconnected");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void doReady(String readyConf) {
        try {
            if (!this.id.equals(0) && this.id.equals(clients.size() - 1)) {
                System.out.println("clientid: " + this.id);
                for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                    entry.getValue().oos.reset();
                    entry.getValue().oos.writeUTF("everyone is ready");
                    entry.getValue().oos.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void doLobby(String playerName) {
        try { // updating players in lobby - sending names
            for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                System.out.println(entry.getKey() + " " + this.id);
                if (entry.getKey().equals(this.id)) {
                    System.out.println("id equals");
                    this.oos.writeUTF(playerName);
                    this.oos.flush();
                } else {
                    System.out.println("send to others");
                    entry.getValue().oos.writeUTF(playerName);
                    entry.getValue().oos.flush();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
