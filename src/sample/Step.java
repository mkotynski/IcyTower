package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Step {
    private double positionY;
    private double positionX;
    private double width;
    private Rectangle rect;
    private Rectangle shape;
    private StackPane stack;
    private Color color;
    private int index;

    public Text getTextInStep() {
        return textInStep;
    }

    public void setTextInStep(Text textInStep) {
        this.textInStep = textInStep;
    }

    private Text textInStep;

    Step(double positionX, double positionY, double width, int i, Color color)
    {
        this.index = i;
        this.stack = new StackPane();
        this.color = color;
        stack.setMinHeight(32);
        textInStep = new Text("-" + index + " - ");
        textInStep.setFill(Color.WHITE);
        shape = new Rectangle(0,0,width,8);
        shape.setFill(Color.rgb(200,200,200,0.5));
        rect = new Rectangle(0,0,width,20);
        rect.setFill(color);
        stack.getChildren().addAll(rect, shape, textInStep);
        StackPane.setAlignment(textInStep,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(shape, Pos.TOP_CENTER);
        StackPane.setAlignment(rect, Pos.BOTTOM_CENTER);
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public StackPane getStack() {
        return stack;
    }

    public void setStack(StackPane stack) {
        this.stack = stack;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
