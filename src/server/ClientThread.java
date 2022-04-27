package server;

import static server.ServerThread.*;
import java.io.*;
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
            System.out.println("Client " + id + " connected");
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
                            System.out.println("in");
                            doChat(split[1]);
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
                for (Map.Entry<Integer, ClientThread> entry : clients.entrySet()) {
                    // entry.getValue().oos.reset();
                    System.out.println("Client " + id + " ready");
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
