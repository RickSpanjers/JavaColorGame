package websockets.messaging;

import domain.Color;
import domain.GameMode;

public interface IClientMessageGenerator {
    void register(String username, String mail, String password);
    void loginPlayer(String account, String password, boolean singlePlayerMode, GameMode gameMode);
    void setGameDifficulty(int difficulty);
    void openLobby();
    void notifyReady();
    void enterSequence(Color c);
    void startNewGame();
    void repeatLastSequence();
    void quitGame();
    void getAllHighScores(GameMode filter);
    void getHighestScore(GameMode gameMode);
    void beginShowingSequences();
}
