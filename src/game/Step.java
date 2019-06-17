package game;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static javafx.geometry.Side.BOTTOM;
import static javafx.geometry.Side.LEFT;

public class Step {
    private double positionY;
    private double positionX;
    private double width;
    private Rectangle rect;
    private Rectangle shape;
    private StackPane stack;
    private Color color;
    private int index;
    private Image stepLeft = new Image(getClass().getResource("/game/images/step1l.png").toExternalForm());
    private Image stepRight = new Image(getClass().getResource("/game/images/step1r.png").toExternalForm());
    private Image stepLeftBlue = new Image(getClass().getResource("/game/images/step1lBLUE.png").toExternalForm());
    private Image stepRightBlue = new Image(getClass().getResource("/game/images/step1rBLUE.png").toExternalForm());
    private Image stepLeftGreen = new Image(getClass().getResource("/game/images/step1lGREEN.png").toExternalForm());
    private Image stepRightGreen = new Image(getClass().getResource("/game/images/step1rGREEN.png").toExternalForm());
    private Image stepLeftRed = new Image(getClass().getResource("/game/images/step1lRED.png").toExternalForm());
    private Image stepRightRed = new Image(getClass().getResource("/game/images/step1rRED.png").toExternalForm());
    private Image stepLeftYellow = new Image(getClass().getResource("/game/images/step1lYELLOW.png").toExternalForm());
    private Image stepRightYellow = new Image(getClass().getResource("/game/images/step1rYELLOW.png").toExternalForm());
    private ImageView sLeft;
    private ImageView sRight;

    private BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/game/images/step1body.png").toExternalForm(),100,25,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(LEFT,0,false, BOTTOM,0,false), BackgroundSize.DEFAULT);
    private BackgroundImage backgroundImageBlue = new BackgroundImage( new Image( getClass().getResource("/game/images/step1bodyBLUE.png").toExternalForm(),100,25,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(LEFT,0,false, BOTTOM,0,false), BackgroundSize.DEFAULT);
    private BackgroundImage backgroundImageGreen = new BackgroundImage( new Image( getClass().getResource("/game/images/step1bodyGreen.png").toExternalForm(),100,25,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(LEFT,0,false, BOTTOM,0,false), BackgroundSize.DEFAULT);
    private BackgroundImage backgroundImageRed = new BackgroundImage( new Image( getClass().getResource("/game/images/step1bodyRed.png").toExternalForm(),100,25,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(LEFT,0,false, BOTTOM,0,false), BackgroundSize.DEFAULT);
    private BackgroundImage backgroundImageYellow = new BackgroundImage( new Image( getClass().getResource("/game/images/step1bodyYellow.png").toExternalForm(),100,25,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(LEFT,0,false, BOTTOM,0,false), BackgroundSize.DEFAULT);



    public Text getTextInStep() {
        return textInStep;
    }

    public void setTextInStep(Text textInStep) {
        this.textInStep = textInStep;
    }

    private Text textInStep;

    Step(double positionX, double positionY, double width, int i)
    {
        this.index = i;
        this.stack = new StackPane();
        stack.setMinHeight(35);
        textInStep = new Text("-" + index + " - ");
        textInStep.setFill(Color.WHITE);
        shape = new Rectangle(0,0,width,8);
        shape.setFill(Color.rgb(200,200,200,0.5)); // hitbox
        sLeft = new ImageView(stepLeft);
        sRight = new ImageView(stepRight);
        sRight.setFitHeight(25);
        sRight.setFitWidth(10);
        sLeft.setFitHeight(25);
        sLeft.setFitWidth(10);

        stack.setBackground(new Background(backgroundImage));
        stack.getChildren().addAll(shape, textInStep, sLeft, sRight);
        StackPane.setAlignment(textInStep,Pos.BOTTOM_CENTER);
        StackPane.setAlignment(shape, Pos.TOP_CENTER);
        StackPane.setAlignment(sLeft, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(sRight, Pos.BOTTOM_RIGHT);
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
    }
    public void changeBackgroundImage(int color)
    {
        if(color == 1)
        {
            stack.setBackground(new Background(backgroundImageBlue));
            sRight.setImage(stepRightBlue);
            sLeft.setImage(stepLeftBlue);
        }
        else if(color == 2)
        {
            stack.setBackground(new Background(backgroundImageGreen));
            sRight.setImage(stepRightGreen);
            sLeft.setImage(stepLeftGreen);
        }
        else if(color == 3)
        {
            stack.setBackground(new Background(backgroundImageRed));
            sRight.setImage(stepRightRed);
            sLeft.setImage(stepLeftRed);
        }
        else if(color == 4)
        {
            stack.setBackground(new Background(backgroundImageYellow));
            sRight.setImage(stepRightYellow);
            sLeft.setImage(stepLeftYellow);
        }
        else
        {
            stack.setBackground(new Background(backgroundImage));
            sRight.setImage(stepRight);
            sLeft.setImage(stepLeft);
        }
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
