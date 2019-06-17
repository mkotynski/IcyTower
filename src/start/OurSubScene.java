package start;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class OurSubScene extends SubScene
{
    private final static String FONT_PATH = "start/resources/HALO____.TTF";
    private final static String BACKGROUND_IMAGE = "/start/images/button.png";

    private boolean isHidden;

    public OurSubScene()
    {
        super(new AnchorPane(),400,300);
        prefWidth(400);
        prefHeight(300);
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource(BACKGROUND_IMAGE).toExternalForm(),400,300,false,false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        Background background = new Background(backgroundImage);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(background);

        isHidden = true;

        setLayoutY(250);
        setLayoutX(1024);
    }

    public void moveSubScene()
    {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane()
    {
        return (AnchorPane) this.getRoot();
    }
}
