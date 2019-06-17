package simongame;

import domain.Color;
import domain.GameMode;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonHighscoreGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;

public interface ISimonGUI {
    void registerPlayer(String username, String mail, String password, ISimonRegisterGUI registerGUI);

    void loginPlayer(String account, String password, ISimonSetupGUI setupGUI, boolean singlePlayerMode, GameMode gameMode);

    void openLobbyForPlayers(ISimonGameGUI gameGUI);

    void setGameDifficulty(int difficulty);

    void notifyReady();

    void startNewGame();

    void beginShowingSequences();

    void quitGame();

    void enterSequence(Color color);

    void repeatLastSequence();

    void getAllHighScores(ISimonHighscoreGUI highscoreGUI, GameMode filter);

    void getHighestScore(GameMode gameMode);
}
