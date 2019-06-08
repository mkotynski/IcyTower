package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {
    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;
    volatile boolean onGround;
    volatile public boolean fallingDown;

    public Rectangle graph;
    Player()
    {
        this.graph = new Rectangle(0,0,40,40);
        this.graph.setFill(Color.BLACK);
        this.onGround = false;
        this.fallingDown = true;
    }
}
