package simongui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import simongame.ISimonGUI;
import domain.SimonColor;
import simongui.interfaces.ISimonGameGUI;

import java.security.Key;

public class GameGUIController implements ISimonGameGUI {

    private ISimonGUI guiLogic;
    private boolean playerMove = false;
    private FillTransition ft = new FillTransition();
    private String[] playerNames = new String[4];


    @FXML
    private Label labelLoggedInAs;
    @FXML
    private Button btnRepeatLast;
    @FXML
    private Button btnToStart;
    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnNotifyReady;
    @FXML
    private Button btnShowSequence;

    //FXML Colors
    @FXML
    private Rectangle colorRed;
    @FXML
    private Rectangle colorGreen;
    @FXML
    private Rectangle colorBlue;
    @FXML
    private Rectangle colorYellow;

    //FXML Labels
    @FXML
    private Label txtPlayer1Value;
    @FXML
    private Label txtPlayer2lbl;
    @FXML
    private Label txtPlayer2Value;
    @FXML
    private Label txtPlayer3lbl;
    @FXML
    private Label txtPlayer3Value;
    @FXML
    private Label txtPlayer4lbl;
    @FXML
    private Label txtPlayer4Value;
    @FXML
    private Label labelDifficultyValue;
    @FXML
    private Label labelTurnValue;
    @FXML
    private Label txtRoundIncrease;

    @FXML
    private void initialize() {
        showMessage("Please press the 'Ready To Play' button to start the game");

        btnNewGame.setOnAction(event -> guiLogic.startNewGame());
        btnRepeatLast.setOnAction(event -> guiLogic.repeatLastSequence());
        btnNotifyReady.setOnAction(event -> guiLogic.notifyReady());

        btnToStart.setOnAction(event -> {
            guiLogic.quitGame();
            Runtime.getRuntime().exit(1);
        });

        btnShowSequence.setOnAction(event -> {
            guiLogic.beginShowingSequences();
            btnShowSequence.setDisable(true);
        });

        //Do not show opponents
        txtPlayer2lbl.visibleProperty().set(false);
        txtPlayer2Value.visibleProperty().set(false);
        txtPlayer3lbl.visibleProperty().set(false);
        txtPlayer3Value.visibleProperty().set(false);
        txtPlayer4lbl.visibleProperty().set(false);
        txtPlayer4Value.visibleProperty().set(false);
        txtRoundIncrease.visibleProperty().set(false);
        btnShowSequence.setDisable(true);

        //On color click
        colorRed.setOnMouseClicked(event -> processColorClick(new domain.Color(SimonColor.RED, "#8c1607", "#ff3232")));
        colorBlue.setOnMouseClicked(event -> processColorClick(new domain.Color(SimonColor.BLUE, "#081189", "#4128ff")));
        colorGreen.setOnMouseClicked(event -> processColorClick(new domain.Color(SimonColor.GREEN, "#275603", "#2bba13")));
        colorYellow.setOnMouseClicked(event -> processColorClick(new domain.Color(SimonColor.YELLOW, "#bf9d04", "#ffe054")));
    }

    @Override
    public void showMessage(final String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simon Game Message");
            alert.setHeaderText("Message for player");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public void notifyStartGame() {
        btnToStart.setDisable(true);
        btnRepeatLast.setDisable(true);
        btnNewGame.setDisable(true);
        btnNotifyReady.setDisable(true);
        playerMove = false;
        btnShowSequence.setDisable(false);
    }

    @Override
    public void notifyWaitOthersMulti() {
        btnToStart.setDisable(true);
        btnRepeatLast.setDisable(true);
        btnNewGame.setDisable(true);
        btnNotifyReady.setDisable(true);
        playerMove = false;
        btnShowSequence.setDisable(true);
    }

    @Override
    public void setGameFinished() {
        btnRepeatLast.setDisable(false);
        btnToStart.setDisable(false);
        btnNewGame.setDisable(false);
        btnNotifyReady.setDisable(true);
        playerMove = false;
    }

    @Override
    public void setLoggedIn(String name, int difficulty) {
        labelLoggedInAs.setText(name);
        txtPlayer1Value.setText(name);
        btnNewGame.setDisable(true);
        btnToStart.setDisable(true);
        btnRepeatLast.setDisable(true);

        switch (difficulty) {
            case 1:
                labelDifficultyValue.setText("Easy");
                break;
            case 2:
                labelDifficultyValue.setText("Medium");
                break;
            case 3:
                labelDifficultyValue.setText("Hard");
                break;
            default:
                break;
        }
    }

    @Override
    public void setOpponentNames(String opponentName, int playerNr) {
        Platform.runLater(() -> {
            switch (playerNr) {
                case 0:
                    txtPlayer1Value.setText(opponentName);
                    playerNames[0] = opponentName;
                    break;
                case 1:
                    txtPlayer2Value.setText(opponentName);
                    txtPlayer2lbl.visibleProperty().set(true);
                    txtPlayer2Value.visibleProperty().set(true);
                    playerNames[1] = opponentName;
                    break;
                case 2:
                    txtPlayer3Value.setText(opponentName);
                    txtPlayer3lbl.visibleProperty().set(true);
                    txtPlayer3Value.visibleProperty().set(true);
                    playerNames[2] = opponentName;
                    break;
                case 3:
                    txtPlayer4Value.setText(opponentName);
                    txtPlayer4lbl.visibleProperty().set(true);
                    txtPlayer4Value.visibleProperty().set(true);
                    playerNames[3] = opponentName;
                    break;
                default:
                    break;
            }
        });
    }


    @Override
    public void startNewGame() {
        Platform.runLater(() -> {
            txtPlayer1Value.setText("YOU");
            labelTurnValue.setText("0");
            btnNewGame.setDisable(true);
            btnRepeatLast.setDisable(true);
            btnToStart.setDisable(true);
            playerMove = false;
            btnShowSequence.setDisable(false);
            resetPlayerNames();
        });
    }

    private void resetPlayerNames() {
        txtPlayer1Value.setText(playerNames[0]);
        txtPlayer2Value.setText(playerNames[1]);
        txtPlayer3Value.setText(playerNames[2]);
        txtPlayer4Value.setText(playerNames[3]);
    }

    @Override
    public synchronized void showSquareToPress(domain.Color color, int speed) {
        ft = new FillTransition(Duration.millis(speed));
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
        showColorTransition(color, speed);
    }


    private void showColorTransition(domain.Color color, int speed) {
        switch(color.getColorCode()){
            case RED:
                ft.setShape(colorRed);
                break;
            case BLUE:
                ft.setShape(colorBlue);
                break;
            case GREEN:
                ft.setShape(colorGreen);
                break;
            case YELLOW:
                ft.setShape(colorYellow);
                break;
        }
        Shape s = ft.getShape();
        ft.setFromValue(Color.web(color.getFromHexcode()));
        ft.setToValue(Color.web(color.getToHexcode()));
        ft.play();
        ft.setOnFinished(e -> {
            ft = new FillTransition(Duration.millis(speed), s, Color.web(color.getToHexcode()), Color.web(color.getFromHexcode()));
            ft.play();
        });
    }


    @Override
    public void setPlayerLoss(int playerNr) {
        Platform.runLater(() -> {
            switch (playerNr) {
                case 0:
                    txtPlayer1Value.setText("LOST");
                    break;
                case 1:
                    txtPlayer2Value.setText("LOST");
                    break;
                case 2:
                    txtPlayer3Value.setText("LOST");
                    break;
                case 3:
                    txtPlayer4Value.setText("LOST");
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void increaseRound() {
        Platform.runLater(() -> {
            int currentTurn = Integer.parseInt(labelTurnValue.getText());
            currentTurn = currentTurn + 1;
            labelTurnValue.setText(String.valueOf(currentTurn));
        });
    }

    @Override
    public void allowPlayerToEnterSequence(boolean allow) {
        playerMove = allow;
    }

    @Override
    public void showNextRoundAnimation(){
        txtRoundIncrease.visibleProperty().set(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), new KeyValue(txtRoundIncrease.layoutYProperty(), 30)));
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.setOnFinished(event -> txtRoundIncrease.visibleProperty().set(false));
        timeline.play();
    }

    private void processColorClick(domain.Color c) {
        if (playerMove) {
            guiLogic.enterSequence(c);
            showSquareToPress(c, 250);
        } else {
            showMessage("You cannot enter a sequence yet");
        }
    }

    public void setGUILogic(ISimonGUI guiLogic) {
        this.guiLogic = guiLogic;
        guiLogic.openLobbyForPlayers(this);
    }

    @Override
    public void gameShutdown() {
        Platform.runLater(() -> Runtime.getRuntime().exit(1));
    }
}
