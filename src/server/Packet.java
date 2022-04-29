package server;

import java.io.*;
import java.util.List;


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

    
    /** 
     * @return Double
     */
    public Double getAngle() {
        return angle;
    }
    
    /** 
     * @return Double
     */
    public Double getY() {
        return y;
    }
    
    /** 
     * @return Double
     */
    public Double getX() {
        return x;
    }
    
    /** 
     * @return Integer
     */
    public Integer getId() {
        return id;
    }
    
    /** 
     * @return Double
     */
    public Double getScaleY() {
        return scaleY;
    }
    
    /** 
     * @return List<Double>
     */
    public List<Double> getObjectX(){
        return this.objectX;
    }
    
    /** 
     * @return List<Double>
     */
    public List<Double> getObjectY(){
        return this.objectY;
    }

   
}
