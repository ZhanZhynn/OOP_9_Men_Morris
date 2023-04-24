package game.Controller;

import game.GameManager;
import game.Main;
import game.Position;
import game.Board;
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
     * Returns the location of the {@code ImageView} passed to the method.
     *
     * @param iv
     *            {@code ImageView} to find out the location of
     * @return location in the corresponding {@code GridPane}
     */
    private Position getLocationOfTile(ImageView iv) {
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
     * Sets the {@code GameManager} object in which the game logic is placed.
     *
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    /**
     * Initializes the drag functionality on a given {@code GridPane} parameter.
     *
     * @param grid
     *            to be allowed to drag from
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

                Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(iv.getImage());
                content.putString(iv.getId());
                db.setContent(content);
                event.consume();
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
     * @param grid
     *            to be allowed to drop on
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
                        iv.setImage(db.getImage());
                        iv.setId(db.getString());
                        event.setDropCompleted(true);

                    }
                }
                event.consume();
            });
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        board = Board.getInstance();

        for (Node i : boardGrid.getChildren()) {
            boardGridChildren.add((ImageView) i);
        }

        initDrag(leftTileGrid);
        initDrag(rightTileGrid);
        initDrag(boardGrid);
        initDrop(boardGrid);

    }














}
