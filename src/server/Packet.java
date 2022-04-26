package server;

import java.io.*;
import client.runners.*;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Double x;
    private Double y;
    private Double angle;

    public Packet(Integer id, Double x, Double y, Double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Double getAngle() {
        return angle;
    }
    public Double getY() {
        return y;
    }
    public Double getX() {
        return x;
    }
    public Integer getId() {
        return id;
    }



   
}
