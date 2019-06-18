package start;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OurButton extends Button {
    private final String FONT_PATH = "src/start/resources/HALO____.TTF";

    //private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/buttonOn.png');";
    //private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background: url('/model/resources/button.png');";
/**
 * @param text tworzymy przycisk o podanym tekscie
 */
    public OurButton(String text) {
        setText(text);
        setButtonFont();
        setPrefHeight(60);
        setPrefWidth(200);
        setButtonReleasedStyle();
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 20));
        }
    }

    private void setButtonPressedStyle() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/start/images/buttonOn.png").toExternalForm(), 200, 60, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        setBackground(background);
        setStyle("-fx-text-fill: grey;");
    }

    private void setButtonReleasedStyle() {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/start/images/button.png").toExternalForm(), 200, 60, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        setBackground(background);
        setStyle("-fx-text-fill: white;");
    }

    private void initializeButtonListeners() {
        setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                setEffect(null);
            }
        });
    }

}
