package start;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import game.Game;

import java.util.ArrayList;
import java.util.List;

public class ViewManager
{
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;

    private OurSubScene helpSubScene;
    private OurSubScene scoreSubScene;

    private OurSubScene sceneToHide;
    private List<OurButton> menuButtons;


    public ViewManager()
    {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();
    }

    private void showSubScene(OurSubScene subScene)
    {
        if(sceneToHide != null)
        {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes()
    {
        helpSubScene = new OurSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new OurSubScene();
        mainPane.getChildren().add(scoreSubScene);
    }

    public Stage getMainStage()
    {
        return mainStage;
    }

    private void addMenuButton(OurButton button)
    {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons()
    {
        createStartButton();
        createScoreButton();
        createHelpButton();
        createExitButton();
    }

    private void createStartButton()
    {
        OurButton startButton = new OurButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(ActionEvent->
        {
            Game gameManager = new Game();
            gameManager.createNewGame(mainStage);
        });
    }

    private void createScoreButton()
    {
        OurButton scoreButton = new OurButton("SCORES");
        addMenuButton(scoreButton);

        scoreButton.setOnAction(ActionEvent->
        {
           showSubScene(scoreSubScene);
        });
    }

    private void createHelpButton()
    {
        OurButton helpButton = new OurButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(ActionEvent->
        {
            showSubScene(helpSubScene);
        });
    }

    private void createExitButton()
    {
        OurButton exitButton = new OurButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(ActionEvent->
        {
            mainStage.close();
        });
    }

    private void createBackground()
    {
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/start/images/back2.png").toExternalForm(),256,256,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        mainPane.setBackground(background);
    }

    private void createLogo()
    {
        ImageView logo = new ImageView( new Image( getClass().getResource("/start/images/logo.png").toExternalForm(),400,200,false,false));
        logo.setLayoutX(350);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(new DropShadow()));

        logo.setOnMouseExited(mouseEvent -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }
}
