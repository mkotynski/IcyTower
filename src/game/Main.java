package game;

import javafx.application.Application;
import javafx.stage.Stage;
import start.ViewManager;

/**
 * @author Kluczynski Patryk
 * @author Kotynski Mateusz
 */
public class Main extends Application {

    /**
     * Tworzymy obiekt który tworzy grę
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            ViewManager manager = new ViewManager();
            primaryStage = manager.getMainStage();
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Uruchomienie całej aplikacji
     * @param args standardowe parametry wchodzace do metody w javie
     */
    public static void main(String[] args) {
        launch(args);
    }
}
