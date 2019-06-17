package repository;

import domain.GameMode;
import domain.Highscore;
import domain.Player;
import java.util.ArrayList;

public class HighscoreRepository {

    private IHighscore highscoreContext;

    public HighscoreRepository(IHighscore context) {
        highscoreContext = context;
    }

    public boolean updateHighscoreByUserId(int userId, Highscore highscore) {
        return highscoreContext.updateHighScoreByUserId(userId, highscore);
    }

    public Highscore getHighscoreByUserId(int userId, GameMode filter) {
        return highscoreContext.getHighScoreByUserId(userId, filter);
    }

    public ArrayList<Player> getAllHighscores() {
        return highscoreContext.getAllHighScores();
    }

    public ArrayList<Player> getAllHighscoresByFilter(GameMode filter){return highscoreContext.getAllHighscoresByFilter(filter);}

    public Player getHighestScore(GameMode filter) {
        return highscoreContext.getHighestScore(filter);
    }


}
