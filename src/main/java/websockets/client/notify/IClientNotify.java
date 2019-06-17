package websockets.client.notify;

import domain.Color;
import domain.Player;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonHighscoreGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;

import java.util.ArrayList;

public interface IClientNotify {
    void setRegisterGUI(ISimonRegisterGUI registerGUI);
    void setSetupGUI(ISimonSetupGUI setupGUI);
    void setGameGUI(ISimonGameGUI gameGUI);
    void setHighScoreGUI(ISimonHighscoreGUI highscoreGUI);
    void notifyRegistrationSuccess();
    void notifyRegistrationFailure();
    void notifyLoginSuccess();
    void notifyLoginFailure();
    void notifySetOpponents(ArrayList<Player> otherPlayers);
    void notifyOpponentLoss(int playerNr);
    void notifyStartGame();
    void notifyClientWait();
    void notifyErrorMsgSetup(String error);
    void notifyErrorMsgGame(String error);
    void notifyLastSequence(ArrayList<Color> lastSequence, int difficulty);
    void notifyGameFinish();
    void notifyRound(ArrayList<Color> sequenceToSend, int difficulty, boolean lost);
    void notifyGameLose(int playerNr);
    void notifySystemReset();
    void notifyAllHighScores(ArrayList<Player> highscores);
    void notifyRecordHolder(Player p);
}
