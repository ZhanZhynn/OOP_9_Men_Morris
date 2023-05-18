package com.example.nine_men_morris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Hee Zhan Zhynn
 *
 * This class is used to start the game and initialise the game UI.
 *
 */


public class Main extends Application {

    public static final String GAME_NAME = "Nine Men Morris";
    public static final String GAME_VERSION = "1.0";
    public static final String FULL_NAME = GAME_NAME + " " + GAME_VERSION;
    public static MediaPlayer mediaPlayer;


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
            //play music
            Media sound = new Media(getClass().getResource("lofi2.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setOnEndOfMedia(new Runnable() {    //loop music
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            mediaPlayer.play();

            //close program when click window close
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });



            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}