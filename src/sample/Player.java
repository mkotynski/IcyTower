package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    volatile private boolean onGround;
    volatile private boolean fallingDown;

    private Rectangle graph;
    public Player()
    {
        this.graph = new Rectangle(0,0,40,40);
        this.graph.setFill(Color.BLACK);
        this.onGround = false;
        this.fallingDown = true;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isFallingDown() {
        return fallingDown;
    }

    public void setFallingDown(boolean fallingDown) {
        this.fallingDown = fallingDown;
    }

    public Rectangle getGraph() {
        return graph;
    }

    public void setGraph(Rectangle graph) {
        this.graph = graph;
    }
}
