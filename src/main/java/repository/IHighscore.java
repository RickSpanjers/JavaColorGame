package repository;

import domain.GameMode;
import domain.Highscore;
import domain.Player;
import java.util.ArrayList;

public interface IHighscore {
    boolean updateHighScoreByUserId(int userId, Highscore highscore);

    Highscore getHighScoreByUserId(int userId, GameMode filter);

    ArrayList<Player> getAllHighScores();

    ArrayList<Player> getAllHighscoresByFilter(GameMode filter);

    Player getHighestScore(GameMode filter);
}
