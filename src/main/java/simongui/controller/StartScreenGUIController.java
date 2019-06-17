package simongui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongame.ISimonGUI;
import simongame.SimonGameGUILogic;

import java.io.IOException;


public class StartScreenGUIController {

    private ISimonGUI guiLogic;
    private ILogger logger = Logger.getInstance();

    @FXML
    private Stage currentScreen = new Stage();
    @FXML
    private Button btn_register;
    @FXML
    private Button btn_start;
    @FXML
    private Button btn_highscores;


    @FXML
    private void initialize() {

        btn_register.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("registerScreen.fxml"));
                Parent root = loader.load();
                currentScreen.setScene(new Scene(root, 540, 465));
                RegisterGUIController controller = loader.getController();
                activateGUILogic();
                controller.setGUILogic(guiLogic);
                currentScreen.show();
                ((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (IOException exception) {
                logger.log(exception.getMessage(), LogLevel.FATAL);
            }
        });

        btn_start.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("setupGameScreen.fxml"));
                Parent root = loader.load();
                currentScreen.setScene(new Scene(root, 540, 465));
                SetupScreenGUIController controller = loader.getController();
                activateGUILogic();
                controller.setGUILogic(guiLogic);
                currentScreen.show();
                ((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (Exception exception) {
                logger.log(exception.getMessage(), LogLevel.FATAL);
            }
        });

        btn_highscores.setOnAction(e ->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("highScoresScreen.fxml"));
                Parent root = loader.load();
                currentScreen.setScene(new Scene(root, 700, 550));
                HighScoreGUIController controller = loader.getController();
                activateGUILogic();
                controller.setGUILogic(guiLogic);
                currentScreen.show();
                ((Node) (e.getSource())).getScene().getWindow().hide();
            } catch (IOException exception) {
                logger.log(exception.getMessage(), LogLevel.FATAL);
            }
        });
    }

    public void setGUILogic(ISimonGUI guiLogic) {
        this.guiLogic = guiLogic;
    }

    private void activateGUILogic(){
        if(guiLogic==null){
            guiLogic = new SimonGameGUILogic();
        }
    }

}
