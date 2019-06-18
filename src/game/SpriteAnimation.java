package game;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private int count;
    private int columns;
    private int offsetX;
    private int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    /**
     * Tworzymy kontruktor
     * @param imageView zmienna do wyswietlenia obrazka
     * @param duration czas trwania
     * @param count licznik
     * @param columns kolumny
     * @param offsetX przesuniecie wzgledem X
     * @param offsetY przesuniecie wzgledem Y
     * @param width szerokosc
     * @param height wysokosc
     */
    public SpriteAnimation(
        ImageView imageView,
        Duration duration,
        int count,   int columns,
        int offsetX, int offsetY,
        int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    /**
     * @param x ustaw przesuniecie wgledem x
     */
    public void setOffsetX(int x){
        this.offsetX = x;
    }
    /**
     * @param y ustaw przesuniecie wgledem y
     */
    public void setOffsetY(int y){
        this.offsetY = y;
    }
    /**
     * @param columns ustaw Kolumny w obrazku (Z ilu animacji sklada sie skakanie)
     */
    public void setColumns(int columns)
    {
        this.columns = columns;
    }
    /**
     * @param count ustaw licznik
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
