package server;

/**
 * ServerThread - a separate Thread that starts the server and listens for incoming connections
 * Saves the connections to a map and gives each new client a unique 
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */

import static client.Constants.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
public class ServerThread extends Thread {
    /** Map of all individual clients that connected to this server (stores ID and ClientThread for each) */
    public static final Map<Integer, ClientThread> clients = Collections.synchronizedMap(new HashMap<>());
    public Integer id = -1;
    @Override public void run() {
        try (ServerSocket sSocket = new ServerSocket(PORT.toInt())) {
            while (true) {
                ++id;
                ClientThread ct = new ClientThread(sSocket.accept(),id);
                clients.put(id, ct);
                ct.start();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
