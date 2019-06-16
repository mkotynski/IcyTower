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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameManager
{

    PrintWriter writer;

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
    private StackPane infos = new StackPane();
    private Stage menuStage;
    private int score = 0;
    private int fullScore = 0;
    private boolean changedLevels = false;
    private boolean changedLevel5 = false;
    private int upScore = 1;

    private double stepsVelocity = 2;

    List<Step> steps = new ArrayList<>();
    List<Rectangle> stepsShapes = new ArrayList<>();

    private GridPane gridPane1;
    private GridPane gridPane2;
    private static String BACKGROUND_IMAGE = "sample/brickWall.png";

    private Text textScore;

    private Text infoTextFaster;

    private boolean isAddCombo;



    public GameManager()
    {

        initializeStage();
        createKeyListeners();
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        initializePlayer();
        mainPane.getChildren().add(gamer);
        gamer.setLayoutX(player.getPositionX());
        gamer.setLayoutY(player.getPositionY());
        mainStage.show();
        mainPane.getChildren().add(player.getSprite());

        createBackground();
        createSteps();

        initializeTextFields();

        player.getSprite().toFront();
        gameLoop();
    }

    private void initializeTextFields()
    {
        textScore = new Text("STEPS: " + score + "\nSCORE: " + fullScore);
        Rectangle rect = new Rectangle(0,0,100,40);
        rect.setFill(Color.GRAY);
        scoreStack.getChildren().addAll(rect, textScore);
        mainPane.getChildren().add(scoreStack);

        infoTextFaster = new Text("FASTER!");
        infoTextFaster.setStyle("-fx-font: 32 arial;");
        infoTextFaster.setFill(Color.RED);
        Rectangle rectx = new Rectangle(0,0,300,100);
        rectx.setFill(Color.rgb(150,0,100,0));
        infos.setLayoutX(100);
        infos.setLayoutY(-100);
        infos.getChildren().addAll(rectx, infoTextFaster);
        mainPane.getChildren().add(infos);
    }

    /*
    Inicjacja ustawienia gracza w grze
     */
    private void initializePlayer()
    {
        player = new Player();
        player.setPositionX(250);
        player.setPositionY(613);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setOnGround(false);
        gravity = new Gravity(1   );
        this.gamer = player.getGraph();
    }

    /*
    Procedura wyswietlajaca wynik gracza w lewym gornym prostokącie
     */
    private void displayScore()
    {
        textScore.setText("STEPS: " + score + "\nSCORE: " + fullScore);
    }

    /*
    Procedura kontrolujaca koniec gry
     */
    private void isGameOver()
    {
        //jezeli pozycja gracza na mapie jest > 800px czyli jest poza oknem wyswietlania to koniec gry
        if(player.getPositionY() > 800) {

            gameTimer.stop();

            try {
                FileReader fileReader = new FileReader("test.txt");

                File plik = new File("score.txt");
                FileReader fileReader2 = new FileReader(plik);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }


    }

    /*
    Procedura ruszajaca schodkami w momencie wystartowania gry
     */
    private void startMovingSteps()
    {
        //Uznaje sie, ze gra wystartowala jak gracz znalazl sie ponad 400px okna gry (czyli jego pozycja Y jest mniejsza niz 400px) ( u gory 0 na dole 750)
        if(player.getPositionY() < 400) startedGame = true;
        if(startedGame)
        {
            moveSteps(); // startujemy jezeli flaga ustawiona na true
            moveBackground();
        }
        else
        {
            if(player.isOnGround() == true) player.setVelocityY(-1);
            //jezeli gra nie wystartowala musimy dzialac naprzeciw grawitacji aby gracz nie spadl se schodka startowego
        }
    }

    /*
    Procedura poruszajaca `kamera' do gory jezeli gracz znajduje sie przy gornym krancu ekranu
     */
    private void moveCameraUp()
    {
        if(player.getPositionY() < 100)
        {
            player.setPositionY(player.getPositionY()-player.getVelocityY()*1.1);
            for(int i = 0; i< steps.size(); i++)
            {
                steps.get(i).setPositionY(steps.get(i).getPositionY()-player.getVelocityY()*1.1);
                if(!isAddCombo)
                {
                    fullScore+=1;
                    isAddCombo = true;
                }
            }

        }
        else isAddCombo = false;
    }

    /*
    Funkcja animujaca wszystkie zachowania gracza
     */
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


        if(player.getPositionX() > 0 && player.getPositionX() < 10) player.setVelocityX(30);
        if(player.getPositionX() > 550 && player.getPositionX() < 560) player.setVelocityX(-30);
        /*if(player.getPositionX() > 555 && player.getPositionX() < 560) player.setVelocityX(player.getVelocityX()*(-1) -5);
        else if(player.getPositionX() > 0 && player.getPositionX() < 5) player.setVelocityX(player.getVelocityX()*(-1) +5);*/


        /*
        Obsluge sprite'ow gracza (do uproszczenia)
         */
        if(player.isOnGround()) {
            if(player.getVelocityX() > 0) { // rucj w prawo sprite chodzenia w prawo
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.setOffsetY(55);
                player.animation.setOffsetX(0);
               // player.animation.play();
            }
            else if(player.getVelocityX() < 0) { // ruch w lewo sprite chodzenia w lewo
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.setOffsetY(111);
                player.animation.setOffsetX(0);
                //player.animation.play();
            }
            else { //jezeli na schodku to ustawiamy sprite wyswietlajacego spirte stojacego na ziemi
                player.animation.setOffsetY(0);
                player.animation.setOffsetX(0);
                player.animation.setColumns(3);
                player.animation.setCount(3);
                player.animation.play();
            }
        }
        else if(player.getVelocityY() != 0 && player.getVelocityX() == 0) // sprite skakania pionowego
        {
            player.animation.setOffsetY(167);
            player.animation.setOffsetX(58);
            player.animation.setColumns(1);
            player.animation.setCount(1);
          //  player.animation.play();
        }
        else if(player.getVelocityY() != 0 && player.getVelocityX() != 0) // sprite skakania w raz z ruchem poziomym
        {
            player.animation.setOffsetY(167);
            player.animation.setOffsetX(8);
            player.animation.setColumns(1);
            player.animation.setCount(1);
           // player.animation.play();
        }
        else
        { // jezeli nie spelniono powyzszych to po prostu sprite stania na schodku
            player.animation.stop();
        }

        /*
        poruszanie spritem (obiektem gracza) po ekranie
         */

        player.getSprite().setLayoutY(player.getPositionY()-30);
        player.getSprite().setLayoutX(player.getPositionX());
    }

    /*
    Petla gry tutaj sie dzieje magia uruchamiane jest wiekszosc procedur :)
     */
    private void gameLoop() {

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                isGameOver();
                startMovingSteps();
                changeStepsVelocity();
                movePlayer();
               moveCameraUp();

                animatePlayer();
                checkShapeIntersection(player.getGraph());

                moveUpStepsAndReIndex();
            }
        };
        gameTimer.start();

    }

    /*
    Procedura sprawdzajaca czy sa wcisniete klawisze jezeli tak to odpowiednie dzialanie
     */
    private void movePlayer()
    {
        if(isLeftKeyPressed && !isRightKeyPressed) // jezeli flaga wcisnietego lewego jest true i flaga prawego jest false (czyli nie wcisniety) to w lewo
        {
            if(player.getPositionX() > - 20) // zeby nie wypadl poza ekran
            {
                player.setVelocityX(-8); // predkosc gracza -8 bo w lewo sie porusza
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed) // jak wyzej (ale w prawo
        {
            if(player.getPositionX() < 560) // zeby nie wypadl poza ekran
            {
                player.setVelocityX(8); // predkosc gracza 8 bo w prawo sie porusza
            }
        }
        if(!isLeftKeyPressed && !isRightKeyPressed) // jezeli zaden nie wcisniety to stoi w miejscu
        {
            player.setVelocityX(0); // predkosc pozioma 0
        }

        if(isSpaceBarPressed) // jezeli wcisnieto spacje to skok w gore
        {
            if(player.isOnGround() == true) {
                if(player.getVelocityX() > 0) player.setVelocityY(-16 + player.getVelocityX() * (-0.75)); // bonusy za predkosc pozioma
                else if(player.getVelocityX() < 0) player.setVelocityY(-16 + player.getVelocityX()*0.75); // bonusy za predkosc pozioma
                else player.setVelocityY(-18); // skok w pionie bez prekosci poziomen (slabszy niz bez predkosci poziomej)
                player.setOnGround(false); // ustawiamy flage ze graczc nie stoi na schodku (nie jest na ziemii)
            }
        }
        if(!isSpaceBarPressed)
        {
            // nic nie rob jak spacja nei wcisnieta
        }
        if(isSuperJump) // super skok dla testow :)
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


    /*
    Procedura umozliwiajaca przesuwanie schodkow ktore juz gracz minal tak aby nie bylo konieczne generowanie nowych
     */
    private void moveUpStepsAndReIndex()
    {
        /* przesuwanie pierwszych 400 schodkow */
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
        /* przesuwanie 400-500 */
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

    }

    private void changeStepsVelocity()
    {
        int s = score/100;
        if(s+2 > stepsVelocity)
        {
            stepsVelocity = s+2;
            infos.setLayoutY(300);
        }
        else
        {
            if(infos.getLayoutY() > -100) infos.setLayoutY(infos.getLayoutY()-2);
        }
    }

    /*
Procedura ustawiania kolorow schodkow (do zmiany na grafike)cc
 */

    /*
    Procedura tworzaca schodki
     */
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
                xPos = (double) rand.nextInt(600-(int)size) + 1;
                if (j == 0) {
                    xPos = 0;
                    size = 600;
                }
                Step step = new Step(xPos, i * 150, size, nr);
                step.changeBackgroundImage(numColor);
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

    /*
    Poruszanie schodkami w ramach gry (schodki `ida` w dol)
     */
    private void moveSteps()
    {
        for(int level = 0; level < 10; level++) {
            for (int i = level*100; i < 100+level*100; i++) {
                steps.get(i).setPositionY(steps.get(i).getPositionY()+stepsVelocity); // 2 to predkosc schodkow
                steps.get(i).getStack().setLayoutY(steps.get(i).getPositionY());
            }
        }
    }


    /*
    Procedura sprawdzajaca kolizje miedzy schodkami a graczem
     */
    private void checkShapeIntersection(Shape block) {
        boolean collisionDetected = false;
        int index = 0;
        for (int i = 0; i<505; i++) {
            Shape static_bloc = steps.get(i).getShape();
            if (static_bloc != block) {
                Shape intersect = Shape.intersect(block, static_bloc);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    collisionDetected = true;
                    index = i;

                }
            }
        }

        int combo = 0;
        if (collisionDetected && player.isFallingDown() == true) {
            player.setVelocityY(stepsVelocity-1); // predkosc_schodkow - 1 ( do zautomatyzowania )
            player.setFallingDown(false);
            player.setOnGround(true);
            /* TUTAJ ZAGNIEZDZONE JEST ZAPISYWANIE SCORE'U */
            if(steps.get(index).getIndex() > score)
            {
                combo = steps.get(index).getIndex() - score;
                combo *=combo;
                fullScore +=combo;
                score = steps.get(index).getIndex();
            }
            displayScore(); // wyswietlanie wyniku w momencie zmiany na nowy
        } else {
            player.setOnGround(false);
        }
    }



    /*
    Procedura nasluchujaca wcisniete klawisze
    Na podstawie wcisnietych klawiszy ustawia flagi wcisnietych klawiszy ;)
     */
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
            if (keyEvent.getCode() == KeyCode.S) { // boosty ( do testow )
                for(int i = 0; i< steps.size(); i++)
                {
                    steps.get(i).setPositionY(steps.get(i).getPositionY()+500);
                }
            }
            if (keyEvent.getCode() == KeyCode.D) { // boosty ( do testow )
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

    /*
    Procedura inicjalizujaca scene grys
     */
    private void initializeStage()
    {
        this.mainPane = new AnchorPane();
        this.mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        this.mainStage = new Stage();
        this.mainStage.setScene(mainScene);
    }


    private void createBackground()
    {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for(int i = 0; i<12; i++)
        {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1,i%3,i/3);
            GridPane.setConstraints(backgroundImage2,i%3,i/3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }
        gridPane2.setLayoutY(-1024);
        mainPane.getChildren().addAll(gridPane1,gridPane2);
    }

    private void moveBackground()
    {
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);

        if(gridPane1.getLayoutY() >= 1024) gridPane1.setLayoutY(-1024);
        if(gridPane2.getLayoutY() >= 1024) gridPane2.setLayoutY(-1024);
    }
}
