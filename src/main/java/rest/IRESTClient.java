package rest;

import domain.GameMode;
import domain.Highscore;
import domain.Player;

public interface IRESTClient {
    Player login(String username, String password);

    boolean register(String username, String mail, String password);

    Highscore getHighscore(int playerId);

    boolean setHighscore(int playerId, int difficulty, int turn, GameMode mode);

    Player getHighestScore(GameMode gameMode);

    String getAllHighscores(GameMode filter);
}
