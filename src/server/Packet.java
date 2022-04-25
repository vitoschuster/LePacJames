package server;

import java.io.*;
import client.runners.*;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Pacman pacman;

    public Packet(int id, Pacman pacman) {
        this.id = id;
        this.pacman = pacman;
    }

    public Integer getId() {
        return id;
    }

    public Pacman getPacman() {
        return pacman;
    }

}
