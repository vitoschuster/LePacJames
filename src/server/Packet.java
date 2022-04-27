package server;

import java.io.*;
import java.util.List;

import client.runners.*;

public class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Double x;
    private Double y;
    private Double angle;
    private Double scaleY;
    private List<Double> objectX;
    private List<Double> objectY;

    public Packet(Integer id, Double x, Double y, Double angle, Double scaleY) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.scaleY = scaleY;
    }
    public Packet(Integer id, List<Double>objectX, List<Double>objectY) {
        this.id = id;
        this.objectX=objectX;
        this.objectY=objectY;
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
    public List<Double> getObjectX(){
        return this.objectX;
    }
    public List<Double> getObjectY(){
        return this.objectY;
    }

   
}
