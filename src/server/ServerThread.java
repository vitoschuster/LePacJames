package server;
import static client.Constants.*;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    @Override public void run() {
        try (ServerSocket sSocket = new ServerSocket(PORT.toInt())) {
            while (true) new ClientThread(sSocket.accept()).start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
