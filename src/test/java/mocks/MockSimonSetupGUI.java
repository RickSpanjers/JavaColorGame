package mocks;

import simongame.ISimonGUI;
import simongui.interfaces.ISimonSetupGUI;

public class MockSimonSetupGUI implements ISimonSetupGUI {

    private boolean login = false;
    private String msg;
    private ISimonGUI game;
    private boolean switched = false;


    /**
     * Set user to logged in
     */
    @Override
    public void loginUser() {
        login = true;
    }


    /**
     * Show last message sent to the player
     * @param message String
     * @param playerName String
     */
    @Override
    public void showMessage(String message, String playerName) {
        msg = message;
    }


    /**
     * Set the logic of the game gui
     * @param game ISimonGUI object
     */
    @Override
    public void setGUILogic(ISimonGUI game) {
        this.game = game;
    }


    /**
     * Set the player to logged in or not
     * @param result boolean true or false
     */
    @Override
    public void isLoggedIn(boolean result) {
        login = result;
    }


    /**
     * Switch current screen after successful login
     */
    @Override
    public void switchAfterSuccessfulLogin() {
        switched = true;
    }


    /**
     * Set login button active or inactive
     * @param active boolean true or false
     */
    @Override
    public void loginButtonActive(boolean active) {

    }


    /**
     * Return if the user is logged in or not
     * @return boolean true or false
     */
    public boolean isLogin() {
        return login;
    }


    /**
     * Return if screen has switched
     * @return boolean true or false
     */
    public boolean hasSwitchedScreen(){
        return switched;
    }


    /**
     * Return last message sent to player
     * @return String message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Return Simon GUI
     * @return ISimonGUI
     */
    public ISimonGUI getGUI() {
        return game;
    }
}
