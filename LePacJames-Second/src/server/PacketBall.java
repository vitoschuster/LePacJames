package server;

import java.io.Serializable;
import java.util.List;

public class PacketBall implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private List<Double> objectX;
    private List<Double> objectY;

    public PacketBall(Integer id, List<Double> objectX, List<Double> objectY) {
        this.id = id;
        this.objectX = objectX;
        this.objectY = objectY;
    }

    public Integer getId() {
        return id;
    }

    public List<Double> getObjectX() {
        return this.objectX;
    }

    public List<Double> getObjectY() {
        return this.objectY;
    }

}
