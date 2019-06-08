package view;

import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

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
    List<OurButton> menuButtons;


    public ViewManager()
    {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        //mainPane.setStyle("-fx-background-color:black;");
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

        //createShipChooserSubScene();
    }

   /* private void createShipChooserSubScene()
    {
        shipChooserScene = new OurSubScene();
        mainPane.getChildren().add(shipChooserScene);

        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE UR SHIP");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(-50);
        shipChooserScene.getPane().getChildren().add(chooseShipLabel);

        shipChooserScene.getPane().getChildren().add(createShipsToChoose());
        shipChooserScene.getPane().getChildren().add(createButtonToStart());
    }

    private HBox createShipsToChoose()
    {
        HBox box = new HBox();
        box.setSpacing(20);
        shipsList = new ArrayList<>();
        for(SHIP ship : SHIP.values())
        {
            ShipPicker shipToPick = new ShipPicker(ship);
            shipsList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(MouseEvent -> {
               for(ShipPicker shipx: shipsList)
               {
                   shipx.setIsCircleChoosen(false);
               }
               shipToPick.setIsCircleChoosen(true);
               choosenShip = shipToPick.getShip();
            });
        }
        box.setLayoutX(300-(118*2));
        box.setLayoutY(80);
        return box;
    }*/

   /* private OurButton createButtonToStart()
    {
        OurButton startButton =  new OurButton("START");
        startButton.setLayoutY(200);
        startButton.setLayoutX(200);
        startButton.setOnAction(actionEvent -> {
            if(choosenShip != null)
            {
                GameManager gameManager = new GameManager();
                gameManager.createNewGame(mainStage);
            }
        });
        return startButton;
    }*/

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
            GameManager gameManager = new GameManager();
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
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("/view/resources/back2.png").toExternalForm(),256,256,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        mainPane.setBackground(background);
    }

    private void createLogo()
    {
        ImageView logo = new ImageView( new Image( getClass().getResource("/view/resources/logo.png").toExternalForm(),400,200,false,false));
        logo.setLayoutX(350);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(new DropShadow()));

        logo.setOnMouseExited(mouseEvent -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }
}
