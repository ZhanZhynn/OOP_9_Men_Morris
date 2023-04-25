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
 */

public class RootLayoutController {


    private Stage stage;
    private Board board;
    private GameManager gameManager;

    private ObservableList<ImageView> boardGridChildren = FXCollections.observableArrayList();

    @FXML
    private GridPane boardGrid;
    @FXML
    private GridPane leftTileGrid;
    @FXML
    private GridPane rightTileGrid;

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
    private void initDrag(GridPane grid) {
        for (Node i : grid.getChildren()) {
            ImageView iv = (ImageView) i;

            iv.setOnDragDetected(event -> { // MouseEvent
                if (iv.getImage() == null) {
                    return;
                }
                if (gameManager.getGamePhase() != GamePhase.PLACEMENT && !grid.getId().equals(boardGrid.getId())) {
                    return;
                }
                //In movement phase, set the initial Position of selected token
                if (gameManager.getGamePhase() == GamePhase.MOVEMENT) {
                    gameManager.setSelectedTokenPosition(getTilePosition(iv));
                }

                if (gameManager.colorOnTurn() == Colour.BLACK && iv.getId().contains("blk") ||
                        gameManager.colorOnTurn() == Colour.WHITE && iv.getId().contains("wht")) {
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
                    if (grid.getId().equals(boardGrid.getId())) {
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
    private void initDrop(GridPane grid) {
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
       gameManager.getBoard().tokenPlacedPositionProperty().addListener((observableValue, oldPosition, newPosition) -> {
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
        for (Node i : boardGrid.getChildren()) {
            boardGridChildren.add((ImageView) i);
        }
        initGameManagerPropertyListeners();

        initDrag(leftTileGrid); //id of grid pane in fxml file
        initDrag(rightTileGrid);
        initDrag(boardGrid);
        initDrop(boardGrid);
    }

}
