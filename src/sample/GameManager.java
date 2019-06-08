package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        player.setPositionY(610);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setOnGround(false);
        gravity = new Gravity(1);
        this.gamer = player.getGraph();
        mainPane.getChildren().add(gamer);
        gamer.setLayoutX(player.getPositionX());
        gamer.setLayoutY(player.getPositionY());
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
                if(player.getPositionY() > 800) gameTimer.stop();
                if(player.getPositionY() < 400) startedGame = true;
                if(startedGame) moveSteps();
                else
                {
                    if(player.isOnGround() == true) player.setVelocityY(-1);
                }
                movePlayer();

                if(player.getPositionY() < 100)
                {
                    player.setPositionY(player.getPositionY()-player.getVelocityY()*1.1);
                    for(int i = 0; i< steps.size(); i++)
                    {
                        steps.get(i).setPositionY(steps.get(i).getPositionY()-player.getVelocityY()*1.1);
                    }

                }


                System.out.println("Player position X: " + player.getPositionX());
                System.out.println("Player position Y: " + player.getPositionY());


                player.setVelocityY(player.getVelocityY()+gravity.getGravity());
                if(player.getVelocityX() > 0 && player.getVelocityX()+a < 10 && a < 5) a+=1;
                else if(player.getVelocityX() < 0 && player.getVelocityX()-a > -10 && a > -5) a+=-1;
                else a = 0;
                player.setVelocityX(player.getVelocityX()+a);
                if(player.getPositionX()+player.getVelocityX() <560 && player.getPositionX()+player.getVelocityX() > -0) player.setPositionX(player.getPositionX()+ player.getVelocityX());
                player.setPositionY(player.getPositionY()+player.getVelocityY());

                //checkHitBox();
                checkShapeIntersection(player.getGraph());
                if(player.isOnGround() == true) player.setFallingDown(false);
                else player.setFallingDown(true);

                if(player.getVelocityY() > -2) player.setFallingDown(true);
                else player.setFallingDown(false);

                gamer.setLayoutX(player.getPositionX());
                gamer.setLayoutY(player.getPositionY());
                System.out.println("vel X: " + player.getVelocityX());
                System.out.println("vel y: " + player.getVelocityY());


                if(player.isOnGround() == false)
                {
                    gamer.setRotate(rotateCombo);
                } else gamer.setRotate(0);
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
            //gamer.setRotate(angle);
            if(player.getPositionX() > - 20)
            {
                player.setVelocityX(-5);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed)
        {
            if(player.getPositionX() < 560)
            {
                player.setVelocityX(5);
            }
        }
        if(!isLeftKeyPressed && !isRightKeyPressed)
        {
            player.setVelocityX(0);
        }

        if(isSpaceBarPressed)
        {
            if(player.isOnGround() == true) {
                if(player.getVelocityX() > 0) player.setVelocityY(-16 + player.getVelocityX() * (-1));
                else if(player.getVelocityX() < 0) player.setVelocityY(-16 + player.getVelocityX());
                else player.setVelocityY(-16);
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
                step.setPositionY(step.getPositionY() - (95+level*200) * 150);
                step.getStack().setLayoutY(step.getPositionY());
                step.getStack().setLayoutX(step.getPositionX());
                steps.add(step);
                stepsShapes.add(new Rectangle(step.getPositionX(), step.getPositionY(), step.getWidth(), 15));
                //mainPane.getChildren().add(stepsShapes.get(i));
                mainPane.getChildren().add(step.getStack());
                j--;
                nr--;
                numColor++;
                if(numColor == 5) numColor = 0;
            }
        }

    }

    private void moveSteps()//komentarz`aaaaaa
    {
        for(int level = 0; level < 10; level++) {
            for (int i = level*100; i < 100+level*100; i++) {
                //if(player.velocityY < 0)
                steps.get(i).setPositionY(steps.get(i).getPositionY()+5);
                //else if(player.velocityY > 1) steps.get(i).positionY  -=1;
                steps.get(i).getStack().setLayoutY(steps.get(i).getPositionY());
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
            Shape static_bloc = steps.get(i).getShape();
            if (static_bloc != block) {
                //static_bloc.setFill(Color.GREEN);
                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    index = i;

                }
            }
        }

        if (collisionDetected && player.isFallingDown() == true) {
            player.setVelocityY(4); // predkosc_schodkow
            player.setFallingDown(false);
            player.setOnGround(true);
            score = steps.get(index).getIndex();
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
            if (keyEvent.getCode() == KeyCode.A) {
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
