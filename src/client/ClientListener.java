package client;

import static client.Constants.*;
import client.*;
import client.runners.*;
import java.io.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import server.Packet;
import javafx.geometry.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientListener extends Thread {
    private String address;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Game game;
    private ControllerLobby lobby;
    private String myName;
    private Integer id = -1;
    private int k = 0;
    private boolean isReady = false;
    private boolean isFirst = true;
    private List<Double> objectX = new ArrayList<>();
    private List<Double> objectY = new ArrayList<>();
    private boolean isBall = false;

    public ClientListener(String ipAddress, String name, ControllerLobby c) {
        this.address = ipAddress;
        this.lobby = c;
        this.myName = name;
    }

    @Override
    public void run() {
        try {
            doConnect();

            doLobby();

            isReady();

            startGame();

            while (true) {
                sendPacman();
                receivePacman();
                sendGhost();
                receiveGhost();
                if (!isBall) {
                    sendBalls();
                    receiveBalls();
                }
                Thread.sleep(25);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Try to connect the client to the server with inputted ipaddress on port 1234
     * saving unique client id after connection
     */
    private void doConnect() {
        try {
            socket = new Socket(address, PORT.toInt());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            this.id = this.ois.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Waiting for players to connect to the lobby
     * Listening for new players and displaying in lobby
     */
    private void doLobby() {
        try {
            lobby.displayName(myName);
            lobby.btnReady.setDisable(true);
            while (k < 1) { // lobby loop
                oos.writeObject("CONNECT:" + myName);
                oos.flush();
                String anotherName = ois.readUTF();
                // System.out.println(anotherName + " " + myName);
                if (!anotherName.equals(myName)) {
                    lobby.displayName(anotherName);
                    k++;
                    this.lobby.btnReady.setDisable(false);
                }
                Thread.sleep(FPS.toInt()); // 30 fps
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return true when button is pressed
     */
    private void isReady() {
        while (!this.lobby.btnReady.isDisabled()) {
            try {
                Thread.sleep(FPS.toInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            oos.writeObject("READY:" + "iamReady");
            oos.flush();

            String readyConf = ois.readUTF();
            while (!readyConf.equals("everyone is ready"))
                readyConf = ois.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game
     */
    private void startGame() {
        Platform.runLater(() -> {
            game = new Game(new Court(lobby.stage), this.id, true, 2);
            lobby.stage.setScene(new Scene(game, W.toInt(), H.toInt()));
            lobby.stage.show();
        });
    }

    /**
     * Send Packet
     */

    private void sendPacman() {
        if (game == null || game.p == null)
            return; // mutka mvp

        Packet packet = new Packet(this.id, game.p.getTranslateX(), game.p.getTranslateY(), game.p.angle,
                game.p.getScaleY());
        try {
            this.oos.writeObject(packet);
            this.oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get packet
     */

    private void receivePacman() {
        if (game == null) return;

        try {
            Object obj = this.ois.readObject();
            if (obj instanceof Packet) {
                Packet packet = (Packet) obj;
                Platform.runLater(() -> {
                    game.runners.get(1).setTranslateX(packet.getX());
                    game.runners.get(1).setTranslateY(packet.getY());
                    game.runners.get(1).angle = packet.getAngle();
                    game.runners.get(1).setScaleY(packet.getScaleY());
                });
                // System.out.println("fake pacman" + packet.getPacman().getTranslateX());
                // game.runners.set(1, packet.getPacman());
                // System.out.println("Pakcet received");
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void sendGhost() {

        if (game == null || id > 0)
            return;

        game.runners.stream().filter(Ghost.class::isInstance).forEach(g -> {
            objectX.add(g.getTranslateX());
            objectY.add(g.getTranslateY());
        });

        Packet packet = new Packet(id, objectX, objectY);
        try {
            this.oos.reset();
            this.oos.writeObject(packet);
            this.oos.flush();
            objectX.removeAll(objectX);
            objectY.removeAll(objectY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void receiveGhost() {
        if (game == null || id < 1)
            return;

        try {

            Object obj = this.ois.readObject();
            if (obj instanceof Packet) {
                Packet packet = (Packet) obj;
                Platform.runLater(() -> {
                    for (int i = 0; i < game.ghosts.size(); i++) {
                        game.ghosts.get(i).setTranslateX(packet.getObjectX().get(i));
                        game.ghosts.get(i).setTranslateY(packet.getObjectY().get(i));
                    }
                });
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void sendBalls() {
        if (game == null || id > 0)
            return;

        game.balls.forEach(ballList -> ballList.forEach(ball -> {
            objectX.add(ball.getTranslateX());
            objectY.add(ball.getTranslateY());
        }));

        Packet packet = new Packet(id, objectX, objectY);
        try {
            this.oos.reset();
            this.oos.writeObject(packet);
            this.oos.flush();
            objectX.removeAll(objectX);
            objectY.removeAll(objectY);
            isBall = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveBalls() {
        if (game == null || id < 1)
            return;

        try {
            Object obj = this.ois.readObject();
            isBall = true;
            if (obj instanceof Packet) {
                Packet packet = (Packet) obj;
                Platform.runLater(() -> {
                    int i = 0;
                    for (List<Ball> x : game.balls) {
                        for (int k = 0; k < x.size(); k++, i++) {
                            x.get(k).setTranslateX(packet.getObjectX().get(i));
                            x.get(k).setTranslateY(packet.getObjectY().get(i));
                        }
                    }
                });
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
