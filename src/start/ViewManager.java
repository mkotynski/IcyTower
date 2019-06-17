package start;

import game.Score;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import game.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private Score[] scores = new Score [6];
    private Label[] scoreLabels = new Label[5];

    public ViewManager()
    {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        for(int i = 0; i<5;i++) scores[i] = new Score("n",0);
        for(int i = 0; i<5;i++) scoreLabels[i] = new Label("");
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

    @SuppressWarnings("Duplicates")
    private void getBestScores()throws FileNotFoundException
    {
        File file = new File("src/game/score.txt");
        Scanner in = new Scanner(file);
        File fileNames = new File("src/game/scoreNames.txt");
        Scanner inNames = new Scanner(fileNames);
        for (int i=0;i<5;i++)
        {
            scores[i].setFullScore(Integer.parseInt(in.nextLine()));
            scores[i].setWho(inNames.nextLine());
        }
        in.close();
        inNames.close();

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
            try { getBestScores(); }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }

            Label label = new Label("HIGH SCORES");
            label.setFont(new Font("Arial", 20));
            label.setTextFill(Color.WHITE);

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(60, 0, 0, 40));
            vbox.getChildren().addAll(label);
            for(int i = 0;i<5;i++)
            {
                scoreLabels[i].setText(scores[i].getWho() + " - " + scores[i].getFullScore());
                scoreLabels[i].setTextFill(Color.WHITE);
                vbox.getChildren().add(scoreLabels[i]);
            }

            scoreSubScene.getPane().getChildren().add(vbox);
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
