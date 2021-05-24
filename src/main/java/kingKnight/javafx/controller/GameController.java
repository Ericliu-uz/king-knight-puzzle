package kingKnight.javafx.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.inject.Inject;

import kingKnight.results.GameResult;
import kingKnight.state.Cell;
import org.tinylog.Logger;

import kingKnight.results.GameResultDao;
import kingKnight.state.KingKnightState;
import util.javafx.ControllerHelper;
import util.javafx.Stopwatch;


/**
 * UI control
 */
public class GameController {

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopwatchLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpFinishButton;

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    private KingKnightState gameState;

    private Stopwatch stopwatch = new Stopwatch();

    private String playerName;

    private IntegerProperty steps = new SimpleIntegerProperty();

    private Instant startTime;

    private List<Image> chessImages;

    private boolean pickedChess = true;

    private int oriX, oriY; //oriX, oriY: The location of picked chess.


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @FXML
    public void initialize() {
        chessImages = List.of(
                new Image(getClass().getResource("/images/king.png").toExternalForm()),
                new Image(getClass().getResource("/images/knight.png").toExternalForm())

        );

        stepsLabel.textProperty().bind(steps.asString());
        stopwatchLabel.textProperty().bind(stopwatch.hhmmssProperty());
        Platform.runLater(() -> messageLabel.setText(String.format("Good luck, %s!", playerName)));
        resetGame();
    }

    private void resetGame() {
        gameState = new KingKnightState();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ImageView view = (ImageView) gameBoard.getChildren().get(i * 8 + j);
                view.setImage(null);
            }
        }
        ImageView king_view = (ImageView) gameBoard.getChildren().get(42);
        king_view.setImage(chessImages.get(0));
        ImageView knight_view = (ImageView) gameBoard.getChildren().get(43);
        knight_view.setImage(chessImages.get(1));

        pickedChess = true;
        steps.set(0);
        startTime = Instant.now();
        if (stopwatch.getStatus() == Animation.Status.PAUSED) {
            stopwatch.reset();
        }
        stopwatch.start();
    }

    //    private void bindGameStateToUI() {
//    ImageView king_view = (ImageView) gameBoard.getChildren().get(75);
    //        king_view.setImage(chessImages.get(gameState.getMatrix()[gameState.getKingRow()][gameState.getKingCol()].getValue()-1));
//    ImageView knight_view = (ImageView) gameBoard.getChildren().get(76);
//        knight_view.setImage(chessImages.get(gameState.getMatrix()[gameState.getKnightRow()][gameState.getKnightCol()].getValue()-1));
//    }

//    private void bindGameStateToUI() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                var imageView = (ImageView) gameBoard.getChildren().get(i * 3 + j);
//                var p = gameState.cubeProperty(i, j);
//                imageView.imageProperty().bind(
//                        new ObjectBinding<Image>() {
//                            {
//                                super.bind(p);
//                            }
//                            @Override
//                            protected Image computeValue() {
//                                return cubeImages.get(p.get().getValue());
//                            }
//                        }
//                );
//            }
//        }
//        gameState.solvedProperty().addListener(this::handleSolved);
//    }


    @FXML
    public void handleClickOnCell(MouseEvent mouseEvent) {
//        Integer col = 0;
//        Integer row = 0;
        var col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        if (col == null){col = 0;}
        var row = GridPane.getRowIndex((Node) mouseEvent.getSource());
//        System.out.println(Cell.KNIGHT);
        if (pickedChess) {
            Logger.debug("Chess ({}, {}) is picked", row, col);
            if (gameState.canMoveToNext(row,col)) {
                pickedChess = false;
                oriX = row;
                oriY = col;
            } else {
                Logger.debug("Can not move");
            }
        } else {
            Logger.debug("Destination ({}, {}) is picked", col, row);
            Cell[][] m = gameState.getMatrix();
            gameState.moveToNext(col,row, m[oriX][oriY]);

//            gameState.setMatrix(row, col, m[oriX][oriY]);
//            gameState.setMatrix(oriX, oriY, Cell.EMPTY);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(m[i][j]);
                }
                System.out.println();
            }

            steps.set(steps.get() + 1);
            pickedChess = true;

            if (gameState.canChangeImg) {
                changeImg(oriY, oriX, col, row);
            }
            handleSolved();
        }
    }

    private void changeImg( int oriY,int oriX, int destY, int destX) {
        ImageView original = (ImageView) gameBoard.getChildren().get(oriX * 8 + oriY);
        ImageView dest = (ImageView) gameBoard.getChildren().get(destX * 8 + destY);
        dest.setImage(original.getImage());
        original.setImage(null);
//        Cell[][] matrix = gameState.getMatrix();
//        int boardLength = matrix.length;
//        for (int i = 0; i < boardLength; i++) {
//            for (int j = 0; j < boardLength; j++) {
//                if (matrix[i][j] == Cell.KING) {
////                    ImageView king_view = (ImageView) gameBoard.getChildren().get();
//                    king_view.setImage(chessImages.get(gameState.getMatrix()[gameState.getKingRow()][gameState.getKingCol()].getValue() - 1));
//                } else if (matrix[i][j] == Cell.KNIGHT) {
////                    ImageView knight_view = (ImageView) gameBoard.getChildren().get();
//                    knight_view.setImage(chessImages.get(gameState.getMatrix()[gameState.getKnightRow()][gameState.getKnightCol()].getValue() - 1));
//                } else {
//
//                }
//            }
//        }
    }

    private void handleSolved() {
        if (gameState.isSolved()) {
            Logger.info("Player {} has solved the game in {} steps", playerName, steps.get());
            stopwatch.stop();
            messageLabel.setText(String.format("Congratulations, %s!", playerName));
            resetButton.setDisable(true);
            giveUpFinishButton.setText("Finish");
        }
    }

    public void handleResetButton(ActionEvent actionEvent) {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Logger.info("Resetting game");
        stopwatch.stop();
        resetGame();
    }

    public void handleGiveUpFinishButton(ActionEvent actionEvent) throws IOException {
        var buttonText = ((Button) actionEvent.getSource()).getText();
        Logger.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            stopwatch.stop();
            Logger.info("The game has been given up");
        }
        Logger.debug("Saving result");
        gameResultDao.persist(createGameResult());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader, "/fxml/highscores.fxml", stage);
    }

    private GameResult createGameResult() {
        return GameResult.builder()
                .player(playerName)
                .solved(gameState.isSolved())
                .duration(Duration.between(startTime, Instant.now()))
                .steps(steps.get())
                .build();
    }

}