package simongui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongame.ISimonGUI;
import simongui.interfaces.ISimonRegisterGUI;

import java.io.IOException;

public class RegisterGUIController implements ISimonRegisterGUI {

    private ISimonGUI guiLogic;
    private ILogger logger = Logger.getInstance();

    @FXML
    private Stage currentScreen = new Stage();
    @FXML
    private Button btn_back;
    @FXML
    private Button btn_doregister;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;
    @FXML
    private TextField txt_mail;


    @FXML
    private void initialize() {
        String startScreen = "startScreen.fxml";
        btn_back.setOnAction(e -> {
            switchScreen(startScreen);
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
        btn_doregister.setOnAction(e -> {
            registerPlayer();
            switchScreen(startScreen);
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
    }

    @Override
    public void setGUILogic(ISimonGUI guiLogic) {
        this.guiLogic = guiLogic;
    }

    @Override
    public void registerPlayer() {
        if (txt_username.getText().isEmpty() || txt_mail.getText().isEmpty() || txt_password.getText().isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            guiLogic.registerPlayer(txt_username.getText(), txt_mail.getText(), txt_password.getText(), this);
        }
    }

    @Override
    public void showMessage(final String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simon Game Message");
            alert.setHeaderText("System message");
            alert.setContentText(message);
            alert.showAndWait();
        });

    }

    @Override
    public void completeRegistration() {
        Platform.runLater(() -> switchScreen("startScreen.fxml"));
    }

    private void switchScreen(String screenName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(screenName));
            Parent root = loader.load();
            currentScreen.setScene(new Scene(root, 540, 465));
            StartScreenGUIController controller = loader.getController();
            controller.setGUILogic(guiLogic);
            currentScreen.show();
        } catch (IOException e) {
            logger.log(e.getMessage(), LogLevel.FATAL);
        }
    }
}
