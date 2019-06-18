package game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    volatile private boolean onGround;
    volatile private boolean fallingDown;


    private Image IMAGE = new Image("game/sprites.png");

    private static final int COLUMNS  =   3;
    private static final int COUNT    =  3;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTHIMG   = 37;
    private static final int HEIGHTIMG   = 56;
    private ImageView sprite;

    SpriteAnimation animation;

    private Rectangle graph;

    /**
     * Konstruktor tworzacy gracza
     *
     */
    public Player()
    {
        this.graph = new Rectangle(0,0,20,20);
        this.graph.setFill(Color.rgb(200,200,200,0.5));
        this.onGround = false;
        this.fallingDown = true;
        setSprite();
    }

    private void setSprite()
    {
        this.sprite = new ImageView(IMAGE);
        sprite.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTHIMG, HEIGHTIMG));

        animation = new SpriteAnimation(
                sprite,
                Duration.millis(600),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTHIMG, HEIGHTIMG
        );
        animation.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Metoda do wyswietlania sprite'a
     * @return sprite wyswietla sprite'a na ekranie
     */
    public ImageView getSprite()
    {
        return sprite;
    }

    /**
     * @return positionX zwraca pozycje X gracza
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * @param positionX ustawia pozycje X gracza
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }
    /**
     * @return positionY zwraca pozycje Y gracza
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * @param positionY ustawia pozycje Y gracza
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * @return velocityX zwraca prędkość X
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * @param velocityX ustawia prędkość X
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }
    /**
     * @return velocityY zwraca prędkość Y
     */
    public double getVelocityY() {
        return velocityY;
    }
    /**
     * @param velocityY ustawia prędkość Y
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * @return zwraca czy gracz jest na ziemi
     */
    public boolean isOnGround() {
        return onGround;
    }

    /**
     * @param onGround ustawiam pole czy gracz jest na ziemi
     */
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    /**
     * @return metoda zwracajaca czy gracz spada na dol
     */
    public boolean isFallingDown() {
        return fallingDown;
    }

    /**
     * @param fallingDown ustawiam czy gracz spada w dol
     */
    public void setFallingDown(boolean fallingDown) {
        this.fallingDown = fallingDown;
    }

    /**
     * @return metoda zwraca grafike na ekran
     */
    public Rectangle getGraph() {
        return graph;
    }

 /*   public void setGraph(Rectangle graph) {
        this.graph = graph;
    }*/
}
