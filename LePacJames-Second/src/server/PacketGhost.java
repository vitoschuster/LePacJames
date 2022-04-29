package server;

import java.io.*;


public class PacketGhost implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Double x;
    private Double y;
    private Double angle;
    private Double scaleY;

    public PacketGhost(Integer id, Double x, Double y, Double angle, Double scaleY) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.scaleY = scaleY;
        
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
    public Double getScaleY() {
        return scaleY;
    }


   
}