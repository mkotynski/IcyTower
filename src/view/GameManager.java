package view;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
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

    private int angle;
    private int rotateCombo;
    double rightA = 0;
    double leftA = 0;
    boolean startedGame = false;
    double a = 0;
    StackPane scoreStack = new StackPane();
    Stage menuStage;
    int score = 0;

    boolean isGravity = true;

    List<Step> steps = new ArrayList<>();
    List<Rectangle> stepsShapes = new ArrayList<>();

    public GameManager()
    {
        initializeStage();
        createKeyListeners();
        //createNewGame();
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        player = new Player();
        player.positionX = 250;
        player.positionY = 610;
        player.velocityX = 0;
        player.velocityY = 0;
        player.onGround = false;
        gravity = new Gravity(1);
        this.gamer = player.graph;
        mainPane.getChildren().add(gamer);
        gamer.setLayoutX(player.positionX);
        gamer.setLayoutY(player.positionY);
        mainStage.show();
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

    private void gameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
               // if(player.positionY > 800) gameTimer.stop();
                if(player.positionY < 400) startedGame = true;
                if(startedGame) moveSteps();
                else
                {
                    if(player.onGround == true) player.velocityY = -1;
                }
                movePlayer();

                if(player.positionY < 100)
                {
                    player.positionY -=player.velocityY*1.1;
                    for(int i = 0; i< steps.size(); i++)
                    {
                        steps.get(i).positionY -=player.velocityY*1.1;
                    }

                }

                /*if(player.positionY > 600)
                {
                    player.positionY -=5;

                }*/

                System.out.println("Player position X: " + player.positionX);
                System.out.println("Player position Y: " + player.positionY);


                player.velocityY += gravity.getGravity();
                if(player.velocityX > 0 && player.velocityX+a < 10 && a < 5) a+=1;
                else if(player.velocityX < 0 && player.velocityX-a > -10 && a > -5) a+=-1;
                else  a = 0;
                player.velocityX += a;
                if(player.positionX+player.velocityX <560 && player.positionX+player.velocityX > -0) player.positionX += player.velocityX;
                player.positionY += player.velocityY;

                //checkHitBox();
                checkShapeIntersection(player.graph);
                if(player.onGround == true) player.fallingDown = false;
                else player.fallingDown = true;

                //if(isGravity == true) gravity.setGravity(1);
               // else gravity.setGravity(0);
                if(player.velocityY > -2) player.fallingDown = true;
                else player.fallingDown = false;

                gamer.setLayoutX(player.positionX);
                gamer.setLayoutY(player.positionY);
                System.out.println("vel X: " + player.velocityX);
                System.out.println("vel y: " + player.velocityY);


                if(player.onGround == false)
                {
                    //gamer.setRotate(rotateCombo);
                }
                else rotateCombo = 0;
                rotateCombo+=10;
                if(rotateCombo > 360) rotateCombo = 0;

            }
        };
        gameTimer.start();
    }

    private void movePlayer()
    {
        if(isLeftKeyPressed && !isRightKeyPressed)
        {
            gamer.setRotate(angle);
            if(player.positionX > - 20)
            {
                player.velocityX = -5;
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed)
        {
            if(player.positionX < 560)
            {
                player.velocityX = 5;
            }
        }
        if(!isLeftKeyPressed && !isRightKeyPressed)
        {
            player.velocityX = 0;
        }

        if(isSpaceBarPressed)
        {
            if(player.onGround == true) {
                if(player.velocityX > 0)player.velocityY = -16 + player.velocityX * (-1);
                else if(player.velocityX < 0) player.velocityY = -16 + player.velocityX;
                else player.velocityY = -16;
                player.onGround = false;
            }
        }
        if(!isSpaceBarPressed)
        {

        }
        if(isSuperJump)
        {
            if(player.onGround == true) {
                player.velocityY = -100;
                player.onGround = false;
            }
        }
        if(!isSuperJump)
        {

        }
    }

    private void createSteps()
    {
        Random rand = new Random();
        int j;
        int nr;
        double size;
        double xPos;
        Color color = Color.RED;
        int numColor=0;
        for(int level = 0;level < 10;level++) {
            if(numColor == 0) color = Color.RED;
            else if(numColor == 1) color = Color.BLUE;
            else if(numColor == 2)color = Color.GREEN;
            else if(numColor == 3) color = Color.PURPLE;
            else if(numColor == 4) color = Color.YELLOW;
            j=100;
            nr = 100*(level+1);
            for (int i = 0+level*100; i <= 100+level*100; i++) {
                size = (double) rand.nextInt(100) + 150;
                xPos = (double) rand.nextInt(500) + 1;
                if (j == 0) {
                    xPos = 0;
                    size = 1000;
                }
                Step step = new Step(xPos, i * 150, size, nr, color);
                step.positionY = step.positionY - (95+level*200) * 150;
                step.stack.setLayoutY(step.positionY);
                step.stack.setLayoutX(step.positionX);
                steps.add(step);
                stepsShapes.add(new Rectangle(step.positionX, step.positionY, step.width, 15));
                //mainPane.getChildren().add(stepsShapes.get(i));
                mainPane.getChildren().add(step.stack);
                j--;
                nr--;
                numColor++;
                if(numColor == 5) numColor = 0;
            }
        }

    }

    private void moveSteps()
    {
        for(int level = 0; level < 10; level++) {
            for (int i = level*100; i < 100+level*100; i++) {
                //if(player.velocityY < 0)
                steps.get(i).positionY += 5;
                //else if(player.velocityY > 1) steps.get(i).positionY  -=1;
                steps.get(i).stack.setLayoutY(steps.get(i).positionY);
            }
        }
        /*for(int i = 0; i<100; i++)
        {
            double x =stepsShapes.get(i).getLayoutY();
            x-=  player.velocityY-1;
            double x2 = stepsShapes.get(i).getLayoutY();
            x2-=8;
            if(player.velocityY < 0) stepsShapes.get(i).setLayoutY(x);
            else if(player.velocityY > 1) stepsShapes.get(i).setLayoutY(x2);
            //steps.get(i).stack.setLayoutY(steps.get(i).positionY);
        }*/
    }


    private void checkShapeIntersection(Shape block) {
        boolean collisionDetected = false;
        int index = 0;
        for (int i = 0; i<steps.size(); i++) {
            Shape static_bloc = steps.get(i).shape;
            if (static_bloc != block) {
                //static_bloc.setFill(Color.GREEN);
                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    index = i;

                }
            }
        }

        if (collisionDetected && player.fallingDown == true) {
            //block.setFill(Color.BLUE);
            player.velocityY = 4; // predkosc_schodkow - 1
            player.fallingDown = false;
            player.onGround = true;
            score = steps.get(index).index;
            displayScore();
        } else {
            player.onGround = false;
            //block.setFill(Color.GREEN);
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
            if (keyEvent.getCode() == KeyCode.A) {
                player.positionY = 400;
                player.velocityY = 0;
            }
            if (keyEvent.getCode() == KeyCode.H) {
                    isSuperJump = true;
            }
            if (keyEvent.getCode() == KeyCode.S) {
                for(int i = 0; i< steps.size(); i++)
                {
                    steps.get(i).positionY+=500;
                }
            }
            if (keyEvent.getCode() == KeyCode.D) {
                for(int i = 0; i< steps.size(); i++)
                {
                    steps.get(i).positionY-=500;
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

    public Stage getGameStage()
    {
        return mainStage;
    }

}
