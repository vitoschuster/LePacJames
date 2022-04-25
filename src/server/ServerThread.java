package server;
import static client.Constants.*;
import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class ServerThread extends Thread {
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
