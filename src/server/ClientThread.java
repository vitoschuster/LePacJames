package server;
import static server.Network.*;
import java.io.*;
import java.io.ObjectInputStream.GetField;
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
            System.out.println(clients.size());
            while (true) {
                Object obj = ois.readObject();

                if (obj instanceof String) {
                    String message = (String) obj;
                    String[] split = message.split(":");
                    clients.put(this.oos, split[1]);
                    switch (split[0]) {
                        case "CONNECT": 
                            doLobby();
                            break;
                        case "BTNCLICK":
                            
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

  
    
    private void doLobby() {
        try { //updating players in lobby - sending names
            for (Map.Entry<ObjectOutputStream, String> entry : clients.entrySet()) {
                if (!entry.getKey().equals(this.oos)) {
                    this.oos.writeUTF(entry.getValue());
                    this.oos.flush();
                } else {
                    entry.getKey().writeUTF(entry.getValue());
                    entry.getKey().flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

