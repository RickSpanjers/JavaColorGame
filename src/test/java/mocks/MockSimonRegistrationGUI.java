package mocks;

import simongame.ISimonGUI;
import simongui.interfaces.ISimonRegisterGUI;

public class MockSimonRegistrationGUI implements ISimonRegisterGUI {

    private boolean registered =false;
    private String msg;
    private ISimonGUI game;


    /**
     * Sets the player to registered
     */
    @Override
    public void registerPlayer() {
        registered = true;
    }


    /**
     * Sets the last message shown to the player
     * @param message String
     */
    @Override
    public void showMessage(String message) {
        msg = message;
    }


    /**
     * Set the GUI Logic
     * @param guiLogic GUI Logic
     */
    @Override
    public void setGUILogic(ISimonGUI guiLogic) {
        game = guiLogic;
    }


    /**
     * Complete the registration and give message
     */
    @Override
    public void completeRegistration() {
        showMessage("Successful registration");
    }


    /**
     * Get the last message given to the player
     * @return String message
     */
    public String getMsg() {
        return msg;
    }


    /**
     * Get the Simon Game GUI Logic
     * @return
     */
    public ISimonGUI getGame() {
        return game;
    }


    /**
     * Return if the player is registered or not
     * @return boolean true or false
     */
    public boolean isRegistered() {
        return registered;
    }
}
