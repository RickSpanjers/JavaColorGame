package simongui.interfaces;

import domain.Color;

public interface ISimonGameGUI {
    void showMessage(final String message);

    void notifyStartGame();

    void notifyWaitOthersMulti();

    void setGameFinished();

    void startNewGame();

    void showSquareToPress(Color color, int speed);

    void setPlayerLoss(int playerNr);

    void increaseRound();

    void setLoggedIn(String name, int difficulty);

    void setOpponentNames(String opponentName, int playerNr);

    void allowPlayerToEnterSequence(boolean allow);

    void gameShutdown();

    void showNextRoundAnimation();
}
