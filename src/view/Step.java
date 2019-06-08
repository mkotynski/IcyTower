package view;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Step {
    public double positionY;
    public double positionX;
    public double width;
    public Rectangle rect;
    public Rectangle shape;
    public StackPane stack;
    private Color color;
    int index;

    Step(double positionX, double positionY, double width, int i, Color color)
    {
        //Color transparentColor = new Color(1,1,1,127);
        this.index = i;
        this.stack = new StackPane();
        this.color = color;
        stack.setMinHeight(45);
        Text text = new Text("-" + index + " - ");
        text.setFill(Color.WHITE);
        shape = new Rectangle(0,0,width,2);
        shape.setFill(Color.rgb(200,200,200,0.5));
        rect = new Rectangle(0,0,width,20);
        rect.setFill(color);
        stack.getChildren().addAll(rect, shape, text);
        StackPane.setAlignment(text,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(shape, Pos.TOP_CENTER);
        StackPane.setAlignment(rect, Pos.BOTTOM_CENTER);
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
    }
}
