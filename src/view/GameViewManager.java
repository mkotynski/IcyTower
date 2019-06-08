package view;

public class GameViewManager
{
    /*private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isSpaceBarPressed;
    private int angle;
    private AnimationTimer gameTimer;
    private int gravity = 0;
    private AnimationTimer jumpTimer;
    private double positionY = 710;
    private double positionX = 250;
    private int direction = 0;//gora
    private double hitBox = 695;
    public boolean jumpState;

    private List<Step> steps = new ArrayList<>();


    public GameViewManager() {
        this.jumpState = false;
        initializeStage();
        createKeyListeners();
    }
    @SuppressWarnings("Duplicates")
    private void createKeyListeners() {
        gameScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT) {
                //ship.setTranslateX(ship.getTranslateX() - 35);
                isLeftKeyPressed = true;
            }
            if(keyEvent.getCode() == KeyCode.RIGHT) {
                //ship.setTranslateX(ship.getTranslateX() + 35);
                isRightKeyPressed = true;
            }
            if(keyEvent.getCode() == KeyCode.SPACE ) {
                direction = 0;
                if (gravity == 0) {
                    double ypreviousPos = ship.getTranslateY();
                    jumpTimer = new AnimationTimer() {
                        @SuppressWarnings("Duplicates")
                        @Override
                        public void handle(long now) {

                            /*if(positionY < hitBox + 1 && positionY > hitBox - 1 && direction == 1){
                                gravity = 0;
                                direction = 0;
                                hitBox = hitBox - 10;
                                ship.setLayoutY(positionY);
                                System.out.println("Nastepny hitbox: " + hitBox);
                                jumpTimer.stop();
                            }
                            else
                            {*//*
                            /*shipetTranslateY(ship.getTranslateY() - 20 + gravity);
                            System.out.println("graw++");*/
                            /*gravity = gravity + 1;
                            if(gravity == 21) direction = 1;
                            if (ypreviousPos <= ship.getTranslateY()) {
                                jumpTimer.stop();
                                gravity = 0;
                                direction = 0;
                                //positionY = positionY + 31;
                            }
                            if (isRightKeyPressed == true) {
                                //gravity = gravity + 1;
                                if (ypreviousPos <= ship.getTranslateY()) {
                                    //jumpTimer.stop();
                                    gravity = 0;
                                    direction = 0;
                                    //positionY = positionY + 31;
                                }
                            }
                            if (isLeftKeyPressed == true) {
                                //gravity = gravity + 1;
                                if (ypreviousPos <= ship.getTranslateY()) {
                                    //jumpTimer.stop();
                                    gravity = 0;
                                    //positionY = positionY + 31;
                                }
                            }
                            if(direction == 0) positionY--;
                            else positionY++;
                        }

                        //}
                    };
                    jumpTimer.start();
                }
            }
        });

        gameScene.setOnKeyReleased(keyEvent ->
        {
            if(keyEvent.getCode() == KeyCode.LEFT) {
                isLeftKeyPressed = false;
            } else if(keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = false;
            }
        });
    }

    private void generateSteps()
    {
        Random rand = new Random();
        for(int i = 0; i<9; i++)
        {
            Step step = new Step(rand.nextInt(450),rand.nextInt(680), rand.nextInt(300)+150);
            steps.add(step);
            System.out.println(step.x1);
            Rectangle rect = new Rectangle(step.x1, step.y1, step.width, 15);
            rect.setFill(Color.TRANSPARENT);

            rect.setStroke(Color.BLACK);
            gamePane.getChildren().add(rect);
        }
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage, SHIP choosenShip)
    {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createShip(choosenShip);
        generateSteps();
        this.positionY = 700;
        createGameLoop();
        gameStage.show();
    }

    private void createShip(SHIP choosenShip)
    {
        ship = new ImageView(new Image(getClass().getResource(choosenShip.getUrl()).toExternalForm()));
        ship.setLayoutY(GAME_HEIGHT - 90);
        ship.setLayoutX(GAME_WIDTH/2);
        gamePane.getChildren().add(ship);
    }

    private void createGameLoop()
    {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveShip();
                if(isSpaceBarPressed == false)
                {
                    if(jumpState == true)
                    {
                        if(gravity == 0)
                        {
                            jumpTimer.stop();
                            System.out.println("OK = 0");
                            jumpState = false;
                        }
                    }
                }
                System.out.println("GRAWITACJA: " + gravity);
                //moveMap();
            }
        };
        gameTimer.start();
    }
    /*private void moveMap()
    {
        ship.setLayoutY(positionY);
        ship.setLayoutX(positionX);
        if(positionY < 710) {
            for (Step s : steps) {
                if (positionY > s.y2 && positionY < s.y1) {
                    if (positionX < s.x2 && positionX > s.x1) {
                        direction = 0;
                        gravity = 0;
                    }
                } else {
                    if (direction == 0) {
                        positionY++;
                    } else if (direction == 1) {
                        double ypreviousPos = ship.getTranslateY();
                        System.out.println("ypreviusPos: " + ypreviousPos);
                        jumpTimer = new AnimationTimer() {
                            @SuppressWarnings("Duplicates")
                            @Override
                            public void handle(long now) {
                                gravity = gravity + 1;
                                ship.setTranslateY(positionY - 20 + gravity);
                                if (gravity == 21) direction = 0;
                            }
                        };
                        jumpTimer.start();

                    }
                }
            }
        }
    }*/

    /*private void moveShip()
    {
        //System.out.println(ship.getLayoutX());
        if(isLeftKeyPressed && !isRightKeyPressed)
        {
            if(angle > -30)
            {
                angle -= 9;
            }
            ship.setRotate(angle);
            if(ship.getLayoutX() >- 20)
            {
                ship.setLayoutX(ship.getLayoutX() - 10);
            }
        }
        if(isRightKeyPressed && !isLeftKeyPressed)
        {
            if(angle < 30)
            {
                angle += 9;
            }
            ship.setRotate(angle);

            if(ship.getLayoutX() < 522)
            {
                ship.setLayoutX(ship.getLayoutX() + 10);
            }
        }
        if(!isLeftKeyPressed && !isRightKeyPressed)
        {
            if(angle < 0)
            {
                angle +=9;
            }else if(angle > 0)
            {
                angle -= 9;
            }
            ship.setRotate(angle);
        }
        if(isLeftKeyPressed && isRightKeyPressed)
        {

        }
    }*/
}
