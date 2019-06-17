package simongui.controller;

import domain.GameMode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongame.ISimonGUI;
import simongui.interfaces.ISimonSetupGUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetupScreenGUIController implements ISimonSetupGUI {

    private ISimonGUI guiLogic;
    private boolean loggedIn = false;
    private ILogger logger = Logger.getInstance();
    private ToggleGroup togglegroup = new ToggleGroup();

    @FXML
    private Stage currentScreen = new Stage();
    @FXML
    private ComboBox comboDifficulty;
    @FXML
    private ComboBox comboGamemode;
    @FXML
    private Button btnLookForGame;
    @FXML
    private Button btnBack;

    @FXML
    private TextField txtUsernameEmail;
    @FXML
    private TextField txtPassword;

    @FXML
    private RadioButton radioSingleplayer;
    @FXML
    private RadioButton radioMultiplayer;


    @FXML
    private void initialize() {

        comboDifficulty.getItems().addAll("Easy", "Medium", "Hard");
        comboGamemode.getItems().addAll("Classic", "Length Battle", "Time Battle");
        radioMultiplayer.setToggleGroup(togglegroup);
        radioSingleplayer.setToggleGroup(togglegroup);

        btnLookForGame.setOnAction(e -> {
            if(!loggedIn){
                loginUser();
            }
            if (loggedIn && comboDifficulty.getSelectionModel().getSelectedItem() != null) {
                isLoggedIn(false);
                switchScreen("gameScreen.fxml", 700, 550);
                ((Node) (e.getSource())).getScene().getWindow().hide();
            }
        });

        btnBack.setOnAction(e -> {
            switchScreen("startScreen.fxml", 540, 465);
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
    }

    @Override
    public void loginUser() {

        if(!checkFilledFields().isEmpty()){
            String errors = "Unable to login:" + System.lineSeparator();
            for(String error : checkFilledFields()){
                errors += error + System.lineSeparator();
            }
            showMessage(errors, "Player");
        }
        else {
            if (radioSingleplayer.isSelected()) {
               guiLogic.loginPlayer(txtUsernameEmail.getText(), txtPassword.getText(), this, false, getSelectedGameMode());
            }
            else if(radioMultiplayer.isSelected()) {
                guiLogic.loginPlayer(txtUsernameEmail.getText(), txtPassword.getText(), this, true, getSelectedGameMode());
            }
        }
    }

    @Override
    public void setGUILogic(ISimonGUI game) {
        this.guiLogic = game;
    }


    @Override
    public void showMessage(final String message, String playerName) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Simon Game Setup");
            alert.setHeaderText("System message to " + playerName);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private int setGameDifficulty() {
        int difficulty = 1;
        if (comboDifficulty.getSelectionModel().getSelectedItem().equals("Easy")) {
            guiLogic.setGameDifficulty(1);
        } else if (comboDifficulty.getSelectionModel().getSelectedItem().equals("Medium")) {
            guiLogic.setGameDifficulty(2);
            difficulty = 2;
        } else if (comboDifficulty.getSelectionModel().getSelectedItem().equals("Hard")) {
           guiLogic.setGameDifficulty(3);
            difficulty = 3;
        }
        return difficulty;
    }


    private void switchScreen(String screenName, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(screenName));
            Parent root = loader.load();
            currentScreen.setScene(new Scene(root, width, height));

            if (screenName.equals("startScreen.fxml")) {
                StartScreenGUIController controller = loader.getController();
                controller.setGUILogic(guiLogic);
            } else {
                GameGUIController controller = loader.getController();
                controller.setGUILogic(guiLogic);
                controller.setLoggedIn(txtUsernameEmail.getText(), setGameDifficulty());
            }
            currentScreen.show();
        } catch (IOException e) {
            logger.log(e.getMessage(), LogLevel.FATAL);
        }
    }

    private GameMode getSelectedGameMode(){
        GameMode result = GameMode.CLASSIC;
        if(comboGamemode.getSelectionModel().isEmpty()){
            showMessage("Select a GAME MODE!", "Player");
        }
        else{
            if(comboGamemode.getSelectionModel().getSelectedItem().equals("Classic")){
                result = GameMode.CLASSIC;
            }else if(comboGamemode.getSelectionModel().getSelectedItem().equals("Length Battle")){
                result = GameMode.LENGTHBATTLE;
            }else if(comboGamemode.getSelectionModel().getSelectedItem().equals("Time Battle")){
                result = GameMode.TIMEBATTLE;
                showMessage("This mode is still in development!", "Player");
            }
        }
        return result;
    }

    public void isLoggedIn(boolean result){
        loggedIn = result;
    }

    public void switchAfterSuccessfulLogin(){
        Platform.runLater(()->  btnLookForGame.fire());
    }

    @Override
    public void loginButtonActive(boolean active) {
        btnLookForGame.setDisable(active);
    }

    private ArrayList<String> checkFilledFields(){
        ArrayList<String> errors = new ArrayList<>();
        if(comboGamemode.getSelectionModel().isEmpty()) {
            errors.add("No gamemode selected!");
        }
        if(comboDifficulty.getSelectionModel().isEmpty()) {
            errors.add("No difficulty selected!");
        }
        if(txtUsernameEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            errors.add("No username or password filled!");
        }
        if(togglegroup.getSelectedToggle() == null){
           errors.add("Select multiplayer or singleplayer!");
        }
        return errors;
    }

}
