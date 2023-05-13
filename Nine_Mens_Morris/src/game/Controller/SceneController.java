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
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToGameScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("view/RootLayout.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void closeGame(){
//        Platform.exit();
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
