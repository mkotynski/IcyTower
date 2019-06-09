package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager
{
    private static final int HEIGHT = 800;
    private static final int WIDTH = 600;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private Player player;
    private Gravity gravity;
    private AnimationTimer gameTimer;

    private Rectangle gamer;

    private boolean isLeftKeyPressed = false;
    private boolean isRightKeyPressed = false;
    private boolean isSpaceBarPressed = false;
    private boolean isSuperJump = false;

    private int rotateCombo;
    private boolean startedGame = false;
    private double a = 0;
    private StackPane scoreStack = new StackPane();
    private Stage menuStage;
    private int score = 0;
    private boolean changedLevels = false;
    private boolean changedLevel5 = false;
    private int upScore = 1;


    List<Step> steps = new ArrayList<>();
    List<Rectangle> stepsShapes = new ArrayList<>();

    public GameManager()
    {
        initializeStage();
        createKeyListeners();
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        player = new Player();
        player.setPositionX(250);
        player.setPositionY(613);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setOnGround(false);
        gravity = new Gravity(1);
        this.gamer = player.getGraph();
        mainPane.getChildren().add(gamer);
        gamer.setLayoutX(player.getPositionX());
        gamer.setLayoutY(player.getPositionY());
        mainStage.show();

        mainPane.getChildren().add(player.getSprite());

        createSteps();
        gameLoop();
    }

    private void displayScore()
    {
        mainPane.getChildren().remove(scoreStack);
        Text text = new Text("SCORE: " + score);
        Rectangle rect = new Rectangle(0,0,100,20);
        rect.setFill(Color.GRAY);
        scoreStack.getChildren().addAll(rect, text);
        mainPane.getChildren().add(scoreStack);
    }

    private void isGameOver()
    {
        if(player.getPositionY() > 800) gameTimer.stop();
    }

    private void startMovingSteps()
    {
        if(player.getPositionY() < 400) startedGame = true;
        if(startedGame) moveSteps();
        else
        {
            if(player.isOnGround() == true) player.setVelocityY(-1);
        }
    }

    private void moveCameraUp()
    {
        if(player.getPositionY() < 100)
        {
            player.setPositionY(player.getPositionY()-player.getVelocityY()*1.1);
            for(int i = 0; i< steps.size(); i++)
            {
                steps.get(i).setPositionY(steps.get(i).getPositionY()-player.getVelocityY()*1.1);
            }

        }
    }

    private void animatePlayer()
    {
        //ustawienie predkosci pionowej gracza (uzalezniona od dzialajacej grawitacji)
        player.setVelocityY(player.getVelocityY()+gravity.getGravity());

        //przyspiesznie chwilowe gracza w ruchu w poziomie
        if(player.getVelocityX() > 0 && player.getVelocityX()+a < 10 && a < 5) a+=1;
        else if(player.getVelocityX() < 0 && player.getVelocityX()-a > -10 && a > -5) a+=-1;
        else a = 0;

        //predkosc pozioma gracza
        player.setVelocityX(player.getVelocityX()+a);

        //czy gracz nie probuje wyjsc poza mape - aktualizacja pozycji gracza w poziomie
        if(player.getPositionX()+player.getVelocityX() <560 && player.getPositionX()+player.getVelocityX() > -0) player.setPositionX(player.getPositionX()+ player.getVelocityX());

        //aktualizacja pozycji gracza pionie
        player.setPositionY(player.getPositionY()+player.getVelocityY());

        //sprawdzanie czy gracz na ziemi
        if(player.isOnGround() == true) player.setFallingDown(false);
        else player.setFallingDown(true);

        //ustawienie stanu gracza w zaleznosci od predkosci pionowej
        if(player.getVelocityY() > -2) player.setFallingDown(true);
        else player.setFallingDown(false);

        //rotowanie obiektem gracza
        if(player.isOnGround() == false)
        {
            //player.getSprite().setRotate(rotateCombo);
            //player.getGraph().setRotate(rotateCombo);
        } else player.getSprite().setRotate(0);
        rotateCombo+=25;
        if(rotateCombo > 360) rotateCombo = 0;

        //ustawienie gracza na ekranie
        gamer.setLayoutX(player.getPositionX());
        gamer.setLayoutY(player.getPositionY());


        /*if(player.getPositionX() > 555 && player.getPositionX() < 560) player.setVelocityX(player.getVelocityX()*(-1) -5);
        else if(player.getPositionX() > 0 && player.getPositionX() < 5) player.setVelocityX(player.getVelocityX()*(-1) +5);*/


        if(player.isOnGround()) {
            if(player.getVelocityX() > 0) {
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.setOffsetY(55);
                player.animation.setOffsetX(0);
                player.animation.play();
            }
            else if(player.getVelocityX() < 0) {
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.setOffsetY(111);
                player.animation.setOffsetX(0);
                player.animation.play();
            }
            else {
                player.animation.setOffsetY(0);
                player.animation.setOffsetX(0);
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.play();
            }
        }
        else if(player.getVelocityY() != 0 && player.getVelocityX() == 0)
        {
            player.animation.setOffsetY(167);
            player.animation.setOffsetX(58);
            player.animation.setColumns(1);
            player.animation.setCount(1);
            player.animation.play();
        }
        else if(player.getVelocityY() != 0 && player.getVelocityX() != 0)
        {
            player.animation.setOffsetY(167);
            player.animation.setOffsetX(8);
            player.animation.setColumns(1);
            player.animation.setCount(1);
            player.animation.play();
        }
        else
        {
            player.animation.setOffsetY(0);
            player.animation.setOffsetX(0);
            player.animation.setColumns(3);
            player.animation.setCount(3);
            player.animation.play();
        }

        player.getSprite().setLayoutY(player.getPositionY()-30);
        player.getSprite().setLayoutX(player.getPositionX());
    }

    private void gameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //isGameOver();
                startMovingSteps();
                movePlayer();
                moveCameraUp();

                //System.out.println("Player position X: " + player.getPositionX());
                //System.out.println("Player position Y: " + player.getPositionY());

                animatePlayer();
                checkShapeIntersection(player.getGraph());

               //System.out.println("Velocity X: " + player.getVelocityX());
                //System.out.println("Velocity y: " + player.getVelocityY());
                moveUpStepsAndReIndex();
            }
        };
        gameTimer.start();
    }

    private void movePlayer()
    {
        if(isLeftKeyPressed && !isRightKeyPressed)
        {
            if(player.getPositionX() > - 20)
            {
                player.setVelocityX(-8);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed)
        {
            if(player.getPositionX() < 560)
            {
                player.setVelocityX(8);
            }
        }
        if(!isLeftKeyPressed && !isRightKeyPressed)
        {
            player.setVelocityX(0);
        }

        if(isSpaceBarPressed)
        {
            if(player.isOnGround() == true) {
                if(player.getVelocityX() > 0) player.setVelocityY(-16 + player.getVelocityX() * (-0.75));
                else if(player.getVelocityX() < 0) player.setVelocityY(-16 + player.getVelocityX()*0.75);
                else player.setVelocityY(-18);
                player.setOnGround(false);
            }
        }
        if(!isSpaceBarPressed)
        {

        }
        if(isSuperJump)
        {
            if(player.isOnGround() == true) {
                player.setVelocityY(-100);
                player.setOnGround(false);
            }
        }
        if(!isSuperJump)
        {

        }
    }

    private Color setStepsColor(int numColor)
    {
        Color color = Color.RED;
        if(numColor == 0) color = Color.RED;
        else if(numColor == 1) color = Color.BLUE;
        else if(numColor == 2) color = Color.GREEN;
        else if(numColor == 3) color = Color.PURPLE;
        else if(numColor == 4) color = Color.DARKORANGE;
        return color;
    }

    private void moveUpStepsAndReIndex()
    {

        System.out.println(score);
        System.out.println("-- " + upScore);
        if(upScore > 1 && 600*(upScore-1)-((upScore-2)*100) > score && score > 500*(upScore-1) && changedLevel5 == false)
        {
            System.out.println("Przeniesiono");
            for(int i = 404; i<505;i++)
            {
                steps.get(i).setPositionY(steps.get(i).getPositionY() - (500) * 150);
                steps.get(i).setIndex(steps.get(i).getIndex() + 500);
                steps.get(i).getTextInStep().setText("-" + steps.get(i).getIndex() + " - ");
            }
            changedLevel5 = true;
        }
        if(score > 600*(upScore-1)) changedLevel5 = false;
        if(500*upScore > score && score > 400*upScore+((upScore-1)*100) && changedLevels == false)
        {
            System.out.println("ZMIENIONO" + upScore );

            for(int i = 0; i<=403;i++)
            {
                steps.get(i).setPositionY(steps.get(i).getPositionY() - (500) * 150);
                System.out.println(i + "-- " + steps.get(i).getIndex() +  " -- " + steps.get(i).getPositionX() + " " + steps.get(i).getPositionY());
                steps.get(i).setIndex(steps.get(i).getIndex() + 500);
                steps.get(i).getTextInStep().setText("-" + steps.get(i).getIndex() + " - ");
            }
            upScore +=1;
            changedLevels = true;
        }
        else
        {
            changedLevels = false;
        }
        System.out.println(changedLevel5);

    }


    private void createSteps()
    {
        Random rand = new Random();
        int j;
        int nr;
        double size;
        double xPos;
        int numColor = 0;
        for(int level = 0;level < 10;level++) {
            j=100;
            nr = 100*(level+1);
            for (int i = 0+level*100; i <= 100+level*100; i++) {
                size = (double) rand.nextInt(100) + 150;
                xPos = (double) rand.nextInt(500) + 1;
                if (j == 0) {
                    xPos = 0;
                    size = 1000;
                }
                Step step = new Step(xPos, i * 150, size, nr, setStepsColor(numColor));
                step.setPositionY(step.getPositionY() - (95+level*200) * 150);
                step.getStack().setLayoutY(step.getPositionY());
                step.getStack().setLayoutX(step.getPositionX());
                steps.add(step);
                stepsShapes.add(new Rectangle(step.getPositionX(), step.getPositionY(), step.getWidth(), 15));
                if(level <5) mainPane.getChildren().add(step.getStack());
                j--;
                nr--;
            }
            numColor++;
            if(numColor == 5) numColor = 0;
        }

    }

    private void moveSteps()
    {
        for(int level = 0; level < 10; level++) {
            for (int i = level*100; i < 100+level*100; i++) {
                steps.get(i).setPositionY(steps.get(i).getPositionY()+2);
                steps.get(i).getStack().setLayoutY(steps.get(i).getPositionY());
            }
        }
    }


    private void checkShapeIntersection(Shape block) {
        boolean collisionDetected = false;
        int index = 0;
        for (int i = 0; i<500; i++) {
            Shape static_bloc = steps.get(i).getShape();
            if (static_bloc != block) {
                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    index = i;

                }
            }
        }

        if (collisionDetected && player.isFallingDown() == true) {
            player.setVelocityY(1); // predkosc_schodkow
            player.setFallingDown(false);
            player.setOnGround(true);
            if(steps.get(index).getIndex() > score) score = steps.get(index).getIndex();
            displayScore();
        } else {
            player.setOnGround(false);
        }
    }



    private void createKeyListeners() {
        mainScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                isLeftKeyPressed = true;
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = true;
            }
            if (keyEvent.getCode() == KeyCode.SPACE) {
                isSpaceBarPressed = true;
            }
            if (keyEvent.getCode() == KeyCode.F) {
                player.setPositionY(400);
                player.setVelocityY(0);
            }
            if (keyEvent.getCode() == KeyCode.H) {
                    isSuperJump = true;
            }
            if (keyEvent.getCode() == KeyCode.S) {
                for(int i = 0; i< steps.size(); i++)
                {
                    steps.get(i).setPositionY(steps.get(i).getPositionY()+500);
                }
            }
            if (keyEvent.getCode() == KeyCode.D) {
                for(int i = 0; i< steps.size(); i++)
                {
                    steps.get(i).setPositionY(steps.get(i).getPositionY()-500);
                }
            }
        });
        mainScene.setOnKeyReleased(keyEvent ->
        {
            if(keyEvent.getCode() == KeyCode.LEFT) {
                isLeftKeyPressed = false;
            }
            if(keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = false;
            }
            if(keyEvent.getCode() == KeyCode.SPACE) {
                isSpaceBarPressed = false;
            }
            if(keyEvent.getCode() == KeyCode.H) {
                isSuperJump = false;
            }
        });
    }

    private void initializeStage()
    {
        this.mainPane = new AnchorPane();
        this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        this.mainStage = new Stage();
        this.mainStage.setScene(mainScene);
    }

}
