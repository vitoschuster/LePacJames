package server;
import static server.Network.*;
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

    @Override public void run() {
        try {
            this.ois = new ObjectInputStream(this.cSocket.getInputStream());
            this.oos = new ObjectOutputStream(this.cSocket.getOutputStream());
            System.out.println("Client connection");
            System.out.println(clients.size());
            while (true) {
                String message = ois.readUTF();
                String[] split = message.split(":");
                clients.put(this.oos, split[1]);
                switch (split[0]) {
                    case "NAME": {
                        for (Map.Entry<ObjectOutputStream, String> entry : clients.entrySet()) {
                            if (!entry.getKey().equals(this.oos)) {
                                this.oos.writeUTF(entry.getValue());
                                this.oos.flush();
                            } else {
                                entry.getKey().writeUTF(entry.getValue());
                                entry.getKey().flush();
                            }
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

