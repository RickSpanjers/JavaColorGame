package simongui.interfaces;

import simongame.ISimonGUI;

public interface ISimonRegisterGUI {
    void registerPlayer();
    void showMessage(final String message);
    void setGUILogic(ISimonGUI guiLogic);
    void completeRegistration();
}
