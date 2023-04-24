package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import game.Controller.RootLayoutController;


public class Main extends Application {

    public static final String GAME_NAME = "Nine Men Morris";
    public static final String GAME_VERSION = "1.0";
    public static final String FULL_NAME = GAME_NAME + " " + GAME_VERSION;

    private RootLayoutController controller;
    private GameManager gameManager;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        StackPane layout = new StackPane();
//
//        Button button = new Button("Click me");
//        button.setOnAction(e -> System.out.println("Hello world!"));
//        layout.getChildren().add(button);
//        Scene scene = new Scene(layout, 300, 250);
//        stage.setScene(scene);
//        stage.setTitle("JavaFX20");
//        stage.show();

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
            VBox root = loader.load();
            controller = loader.getController();


            controller.setStage(primaryStage);
//            controller.setGameManager(gameManager);

            Platform.setImplicitExit(false);


            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("View/application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle(GAME_NAME);
            primaryStage.getIcons().add(new Image("file:res/icon.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}