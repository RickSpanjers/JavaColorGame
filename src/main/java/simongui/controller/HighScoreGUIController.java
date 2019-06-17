package simongui.controller;

import domain.GameMode;
import domain.Player;
import dto.HighscoreDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongame.ISimonGUI;
import simongui.interfaces.ISimonHighscoreGUI;

import java.util.ArrayList;

public class HighScoreGUIController implements ISimonHighscoreGUI {

    private ISimonGUI guiLogic;
    private ILogger logger = Logger.getInstance();

    @FXML
    private Stage currentScreen = new Stage();
    @FXML
    private Button btnBackToStart;
    @FXML
    private Button btnRecordHolderClassic;
    @FXML
    private Button btnRecordHolderTime;
    @FXML
    private Button btnRecordHolderLength;
    @FXML
    private Button btnHighscoresClassic;
    @FXML
    private Button btnHighscoresTime;
    @FXML
    private Button btnHighscoresLength;
    @FXML
    private TableView tblHighscores;

    //Table data
    private TableColumn userCol = new TableColumn("Player");
    private TableColumn difficultyCol = new TableColumn("Difficulty");
    private TableColumn roundCol = new TableColumn("Round");
    private TableColumn achievedCol = new TableColumn("Achieved");

    @FXML
    private void initialize() {

        setupTable();
        btnBackToStart.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startscreen.fxml"));
                Parent root = loader.load();
                currentScreen.setScene(new Scene(root, 540, 465));
                StartScreenGUIController controller = loader.getController();
                controller.setGUILogic(guiLogic);
                currentScreen.show();
                ((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (Exception exception) {
                logger.log(exception.getMessage(), LogLevel.FATAL);
            }
        });

        btnRecordHolderClassic.setOnAction(e -> guiLogic.getHighestScore(GameMode.CLASSIC));
        btnRecordHolderLength.setOnAction(e->guiLogic.getHighestScore(GameMode.LENGTHBATTLE));
        btnRecordHolderTime.setOnAction(e->showMessage("This mode is still in development!"));
        btnHighscoresClassic.setOnAction(e->{
            guiLogic.getAllHighScores(this, GameMode.CLASSIC );
            btnHighscoresClassic.setDisable(true);
            btnHighscoresTime.setDisable(false);
            btnHighscoresLength.setDisable(false);
        });
        btnHighscoresLength.setOnAction(e->{
            guiLogic.getAllHighScores(this, GameMode.LENGTHBATTLE);
            btnHighscoresLength.setDisable(true);
            btnHighscoresClassic.setDisable(false);
            btnHighscoresTime.setDisable(false);
        });
        btnHighscoresTime.setOnAction(e-> showMessage("This mode is still in development!"));
    }

    @Override
    public void showMessage(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simon Game Highscores");
            alert.setHeaderText("System message");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public void setGUILogic(ISimonGUI guiLogic) {
        this.guiLogic = guiLogic;
        guiLogic.getAllHighScores(this, GameMode.CLASSIC);
        btnHighscoresClassic.setDisable(true);
    }

    @Override
    public void setHighScoresForTable(ArrayList<Player> highscoreArrayList) {
        Platform.runLater(()->{
            ObservableList<HighscoreDTO> data = FXCollections.observableArrayList();
            for (Player p: highscoreArrayList) {
                HighscoreDTO dto = new HighscoreDTO(p.getPlayerName(), p.getHighscore().getRound(), p.getHighscore().getDifficulty(), p.getHighscore().getAchieved());
                data.add(dto);
            }
            userCol.setCellValueFactory(
                    new PropertyValueFactory<Player, String>("playerName")
            );
            roundCol.setCellValueFactory(
                    new PropertyValueFactory<Player, Integer>("round")
            );
            difficultyCol.setCellValueFactory(
                    new PropertyValueFactory<Player, Integer>("difficulty")
            );
            achievedCol.setCellValueFactory(
                    new PropertyValueFactory<Player, String>("achieved")
            );
            tblHighscores.setItems(data);
        });
    }

    private void setupTable() {
        tblHighscores.setEditable(true);
        tblHighscores.getColumns().addAll(userCol, difficultyCol, roundCol, achievedCol);
    }

}
