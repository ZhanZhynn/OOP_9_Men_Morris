package game.Controller;

import game.Board;
import game.GameManager;
import game.Position;
import game.Utils.Colour;
import game.Utils.GamePhase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
                //In movement phase, set the initial Position of selected token
                if (gameManager.getGamePhase() == GamePhase.MOVEMENT) {
                    gameManager.setSelectedTokenPosition(getTilePosition(iv));
                }

                if (gameManager.colorOnTurn() == Colour.BLACK && iv.getId().contains("wht") ||
                        gameManager.colorOnTurn() == Colour.WHITE && iv.getId().contains("blk")) {

                    //check if all token has been placed on board yet or not
                    if (gameManager.getGamePhase() == GamePhase.PLACEMENT && grid.getId().equals(gameBoardGrid.getId())) {
                        System.out.println("NEED TO PLACE ALL TOKEN FIRST");
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
                            event.setDropCompleted(true);
                        } else {
                            System.out.print("CANNOT PLACE");
                        }
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
    }

}
