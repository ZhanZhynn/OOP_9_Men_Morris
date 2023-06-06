package game.Controller;

import java.io.IOException;

import game.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *        This class exclusively handles the switching of scenes.
 *        - Current scenes:
 *          - Main Menu
 *          - Rules
 *          - Game
 *
 */

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private RootLayoutController controller;

    /**
     * Switch to main menu scene from the game scene
     * @param stageFrom
     * @throws IOException
     */
    public void switchToMainMenuScene(Stage stageFrom) throws IOException{
        root = FXMLLoader.load(Main.class.getResource("View/MainMenu.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stageFrom.setScene(scene);
        stageFrom.show();
    }

    /**
     * Switch to rule scene from the main menu
     *
     * @param event
     * @throws IOException
     */
    public void switchToRuleScene(ActionEvent event)throws IOException {
        root = FXMLLoader.load(Main.class.getResource("View/Rules.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to main menu scene from the rules scene
     * @param event
     * @throws IOException
     */
    public void rulesToMainMenu(ActionEvent event)throws IOException {
        root = FXMLLoader.load(Main.class.getResource("View/MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to game mode selection screen from the main menu
     * @param event
     * @throws IOException
     */
    public void switchToModeSelect(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("View/GameMode.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to game scene from the mode select screen
     * @param event
     * @throws IOException
     */
    public void switchToGameScene(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(Main.class.getResource("View/RootLayout.fxml"));
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("View/RootLayout.fxml"));
        VBox root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        controller = loader.getController();
        controller.setStage(stage);
        Platform.setImplicitExit(false);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("View/application.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Nine Men Moris");
        stage.getIcons().add(new Image("file:res/icon.png"));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Close the game
     */
    public void closeGame(){
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Exit Game?");
        alert.setContentText("Do you really want to close the game?");
        alert.initOwner(stage);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(btnYes, btnNo);
        alert.showAndWait();
        if (alert.getResult() == btnYes) {
            Platform.exit();
        }

    }


}
