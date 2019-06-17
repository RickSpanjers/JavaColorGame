package simongui.interfaces;

import simongame.ISimonGUI;

public interface ISimonSetupGUI {
    void loginUser();
    void showMessage(final String message, String playerName);
    void setGUILogic(ISimonGUI game);
    void isLoggedIn(boolean result);
    void switchAfterSuccessfulLogin();
    void loginButtonActive(boolean active);
}
