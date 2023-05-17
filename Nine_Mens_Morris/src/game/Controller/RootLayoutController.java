package game.Controller;

import game.Board;
import game.GameManager;
import game.Main;
import game.Position;
import game.Utils.Colour;
import game.Utils.GamePhase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Hee Zhan Zhynn
 *
 *        This class is the controller for the root layout. It handles the drag and drop functionality of the game.
 *
 *        The drag and drop functionality is implemented using the JavaFX Drag and Drop API.
 *
 *        Reference:
 *          https://www.javatpoint.com/javafx-convenience-methods
 *          https://github.com/OmDharme/Chess---JavaFX
 *          https://github.com/zann1x/MerelsFX*
 */

public class RootLayoutController {


    private Stage stage;
    private Board board;
    private GameManager gameManager;

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    @FXML
    private GridPane gameBoardGrid;     // game board grid is the main game board to place token on
    @FXML
    private GridPane leftPocketGrid;    // pocket grid is initial token placement before game starts
    @FXML
    private GridPane rightPocketGrid;
    @FXML
    private Label playerTurnLabel;      // label to display current player turn
    private SceneController sceneController;    //to handle exit to main menu from game scene

    /**
     * Returns the Position of the image view in the corresponding GridPane.
     *
     * @param iv image view to be checked
     * @return Position of the image view
     */
    private Position getTilePosition(ImageView iv) {
        Integer column = GridPane.getColumnIndex(iv);
        Integer row = GridPane.getRowIndex(iv);
        return new Position(column == null ? 0 : column, row == null ? 0 : row);
    }

    /**
     * Sets the stage of the application.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the game manager of the application.
     *
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    /**
     * Initializes the drag functionality on a given {@code GridPane} parameter.
     *
     * @param grid to be allowed to drag from
     */
    private void initTokenDrag(GridPane grid) {

        for (Node i : grid.getChildren()) {
            ImageView iv = (ImageView) i;

            iv.setOnDragDetected(event -> { // MouseEvent

                if (iv.getImage() == null) {
                    return;
                }
                if (gameManager.getGamePhase() != GamePhase.PLACEMENT && !grid.getId().equals(gameBoardGrid.getId())) {
                    return;
                }

                //draw check
                if(!gameManager.anyMovePossible() && gameManager.getGamePhase() != GamePhase.PLACEMENT){
                    System.out.println("NO MORE MOVESSSSSSS!!!!!");
                    playerTurnLabel.setText("Draw, no more moves available");
                    return;
                }

                //MILL CHECK
                if (gameManager.isMill()) {
                    System.out.println("THERE IS A MILL");
                    //update label
                    playerTurnLabel.setText("Mill formed, " + gameManager.isOtherTurn().toString() + " can remove opponent token");
                    return;
                }

                //In movement phase, set the initial Position of selected token
                if (gameManager.getGamePhase() == GamePhase.MOVEMENT) {
                    gameManager.setSelectedTokenPosition(getTilePosition(iv));
                }

                if (gameManager.colorOnTurn() == Colour.BLACK && iv.getId().contains("blk") ||
                        gameManager.colorOnTurn() == Colour.WHITE && iv.getId().contains("wht")) {

                    //check if all token has been placed on board yet or not
                    if (gameManager.getGamePhase() == GamePhase.PLACEMENT && grid.getId().equals(gameBoardGrid.getId())) {
                        System.out.println("NEED TO PLACE ALL TOKEN FIRST");
                        //update label
                        playerTurnLabel.setText("NEED TO PLACE ALL TOKEN FIRST");
                        return;
                    }

                    Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(iv.getImage());
                    content.putString(iv.getId());
                    db.setContent(content);
                    event.consume();
                }
            });

            iv.setOnDragDone(event -> { // DragEvent
                if (event.getTransferMode() == TransferMode.MOVE) {
                    iv.setImage(null);
                    if (grid.getId().equals(gameBoardGrid.getId())) {
                        iv.setId(null);
                    }
                }

                event.consume();
            });
        }
    }

    /**
     * Initializes the drop functionality on a given {@code GridPane} parameter.
     *
     * @param grid to be allowed to drop to
     */
    private void initTokenDrop(GridPane grid) {
        for (Node i : grid.getChildren()) {
            ImageView iv = (ImageView) i;

            iv.setOnDragOver(event -> { // DragEvent
                Dragboard db = event.getDragboard();
                if (event.getGestureSource() != iv && db.hasImage() && db.hasString() && iv.getId() == null) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            iv.setOnDragDropped(event -> { // DragEvent
                Dragboard db = event.getDragboard();
                if (db.hasImage() && db.hasString()) {
                    if (iv.getId() == null) {
                        Position placePosition = getTilePosition(iv);
                        if (gameManager.validateTokenPlacement(placePosition)) {
                            iv.setImage(db.getImage());
                            iv.setId(db.getString());
                            board.setTokenPlacedPosition(placePosition);
                            gameManager.changePlayerTurn();
                            System.out.println(gameManager.colorOnTurn() + "turn");
                            playerTurnLabel.setText(gameManager.colorOnTurn() + "'s turn");


                            gameManager.updateMillStatus(placePosition);

                            if (gameManager.isMill()) {
                                //update label
                                playerTurnLabel.setText("Mill formed, " + gameManager.isOtherTurn() + " can remove opponent token");
                            }

                            event.setDropCompleted(true);
                        } else {
                            System.out.print("CANNOT PLACE");
                            playerTurnLabel.setText("Token cannot be placed here");
                        }
                    }
                }
                event.consume();
            });
        }
    }


    private void removeTileMill() {
        for (ImageView iv : boardGridChildren) {
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (gameManager.isMill()) {
                    if (iv.getImage() != null && iv.getId() != null) {
                        System.out.println(gameManager.colorOnTurn());
                        if(iv.getId().contains("blk") && gameManager.colorOnTurn() == Colour.BLACK ||
                                iv.getId().contains("wht") && gameManager.colorOnTurn() == Colour.WHITE){
                            Position position = getTilePosition(iv);

                            if (gameManager.removeToken(position)){//if token can be removed
                                iv.setImage(null);
                                iv.setId(null);

                                gameManager.setMill(false);
                                playerTurnLabel.setText(gameManager.colorOnTurn() + "'s turn");


                            }
                            else{
                                System.out.println("TOKEN CANNOT BE REMOVED");
                                //update label
                                playerTurnLabel.setText("Part of mill, cannot remove token");
                            }

                        }
                    }
                }
                //check win
                if (gameManager.checkWin() > 0){
                    System.out.println("WIN");
                    gameManager.setGamePhase(GamePhase.GAMEOVER);

                    if (gameManager.checkWin() == 1){
                        System.out.println("Player 1 WIN");
                        playerTurnLabel.setText("Player 1 WIN");
//                        gameManager.gameOver();
                    }
                    else{
                        System.out.println("Player 2 WIN");
                        playerTurnLabel.setText("Player 2 WIN");

//                        gameManager.gameOver();
                    }
                    try {
                        handleGameover();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                event.consume();

            });
        }
    }

    /**
     * Initializes the listeners for the properties of the game manager.
     *
     */
    private void initGameManagerPropertyListeners() {
       board.tokenPlacedPositionProperty().addListener((observableValue, oldPosition, newPosition) -> {
            if (newPosition != null && gameManager.getGamePhase() == GamePhase.PLACEMENT) {
                gameManager.placeToken(newPosition);
            } else if (gameManager.getGamePhase() == GamePhase.MOVEMENT) {
                gameManager.moveToken(newPosition);
            }
        });
    }

    /**
     * Initializes the controller class. This method is automatically called.
     * It is called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        gameManager = new GameManager();
        board = gameManager.getBoard();
        for (Node i : gameBoardGrid.getChildren()) {
            boardGridChildren.add((ImageView) i);
        }
        initGameManagerPropertyListeners();


        initTokenDrag(leftPocketGrid); //id of grid pane in fxml file
        initTokenDrag(rightPocketGrid);
        initTokenDrag(gameBoardGrid);
        initTokenDrop(gameBoardGrid);
        removeTileMill();

    }

    /**
     * Sets the scene to its default values as set at the very first start.
     */
    private void initWindow() {
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(Main.class.getResource("view/RootLayout.fxml").toExternalForm());

//        if (gameManager.getGamePhase() == GamePhase.GAMEOVER) {
//            gameBoardGrid.getChildren().remove(24);
//        }

        for (ImageView iv : boardGridChildren) {
            iv.setId(null);
            iv.setImage(null);
        }
    }

    /**
     * Dialog box to show the action of new game/quit game button.
     *
     * @param title
     * @param header
     * @param content
     * @param id
     *
     * id = 0 -> new game
     * id = 1 -> quit game
     * id = 2 -> exit to main menu
     *
     *
     */

    private void gameDialog(String title, String header, String content, int id) throws IOException {
        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(stage);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(btnYes, btnNo);
        alert.showAndWait();
        if (id == 0) {
            if (alert.getResult() == btnYes) {
                initWindow();
                initialize();//initalize a new game
//                board.setNewGame(true);
            }
            else {
                //exit to main menu
                sceneController = new SceneController();
                sceneController.switchToMainMenuScene(this.stage);
            }

        } else if (id == 1) {
            if (alert.getResult() == btnYes) {
                Platform.exit();
            }
        }
        else if (id == 2) {
            if (alert.getResult() == btnYes) {
                sceneController = new SceneController();
                sceneController.switchToMainMenuScene(this.stage);
            }
        }

    }
    public void handleGameover() throws IOException {
        gameDialog("Game Over", "DO you wants to start a new game?", "All progress will be lost.", 0);
    }

    /**
     * Handles the action of the new game button.
     */
    public void handleNewGame() throws IOException {
        gameDialog("New Game", "Are you sure you want to start a new game?", "All progress will be lost.", 0);
    }


    /**
     * Handles the action of the new game button.
     */
    public void handleClose() throws IOException {
        gameDialog("Quit Game", "Are you sure you want to quit?", "All progress will be lost.", 1);
    }

    /**
     * Handles the action of exit to main menu
     */
    public void handleMenu() throws IOException {
        gameDialog("Exit to Main Menu", "Are you sure you want to quit?", "All progress will be lost.", 2);
    }


}
