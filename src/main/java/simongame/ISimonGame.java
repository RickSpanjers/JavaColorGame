package simongame;

import domain.Color;
import domain.Game;
import domain.GameMode;
import domain.Player;
import simonalgorithm.ISequenceGenerator;


public interface ISimonGame {
    boolean registerPlayer(String username, String mail, String password);

    boolean loginPlayer(String account, String password, boolean multiplayer, int playerNr, GameMode gameMode);

    void setGameDifficulty(int difficulty);

    boolean notifyReady(int playerNr);

    void startNewGame();

    boolean enterSequence(Color color, int playerNr);

    boolean processRoundResult(int playerNr);

    void setGameVictor();

    String getAllHighScores(GameMode filter);

    Player getRecordHolder(GameMode gameMode);

    Game getCurrentGame();

    boolean isMultiPlayer();

    ISequenceGenerator getGameAlgorithm();

}
