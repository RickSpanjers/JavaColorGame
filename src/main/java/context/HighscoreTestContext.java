package context;

import domain.Game;
import domain.GameMode;
import domain.Highscore;
import domain.Player;
import repository.IHighscore;

import java.util.ArrayList;


public class HighscoreTestContext implements IHighscore {

    private ArrayList<Player> listOfHighScores = new ArrayList<>();
    private String pass = "Test123!";

    public HighscoreTestContext() {
        listOfHighScores.add(new Player(1, "User01", "user01@outlook.com", pass));
        listOfHighScores.add(new Player(2, "User02", "user02@outlook.com", pass));
        listOfHighScores.add(new Player(3, "user03", "user03@outlook.com", pass));
        listOfHighScores.add(new Player(4, "user04", "user04@outlook.com", pass));

        listOfHighScores.get(0).setHighscore(new Highscore(1, 1, GameMode.CLASSIC));
        listOfHighScores.get(1).setHighscore(new Highscore(3, 3, GameMode.CLASSIC));
        listOfHighScores.get(2).setHighscore(new Highscore(4, 4, GameMode.CLASSIC));
        listOfHighScores.get(3).setHighscore(new Highscore(2, 2, GameMode.CLASSIC));
    }

    public boolean updateHighScoreByUserId(int userId, Highscore h) {
        boolean result = false;
        for (Player player : listOfHighScores) {
            if (player.getPlayerId() == userId && player.getHighscore().getDifficulty() < h.getDifficulty() ) {
                if(checkRoundForHighscore(player.getHighscore().getRound(), h.getRound())){
                    player.setHighscore(h);
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean checkRoundForHighscore(int roundFormer, int roundLatter){
        boolean result = false;
        if(roundFormer < roundLatter){
           result = true;
        }
        return result;
    }

    public Highscore getHighScoreByUserId(int userId, GameMode gameMode) {
        Highscore result = null;
        for (Player u : listOfHighScores) {
            if (u.getPlayerId() == userId) {
                result = u.getHighscore();
            }
        }
        return result;
    }

    public ArrayList<Player> getAllHighScores() {
        ArrayList<Player> highScores = new ArrayList<>();
        for (Player player : listOfHighScores) {
            highScores.add(player);
        }
        return highScores;
    }

    @Override
    public ArrayList<Player> getAllHighscoresByFilter(GameMode filter) {
        ArrayList<Player> highScores = new ArrayList<>();
        for (Player player : listOfHighScores) {
            if(player.getHighscore().getGameMode() == filter){
                highScores.add(player);
            }
        }
        return highScores;
    }

    @Override
    public Player getHighestScore(GameMode filter) {
        Player highestScore = new Player(0,null);
        for (Player p : listOfHighScores) {
            if (p.getHighscore().getRound() > highestScore.getHighscore().getRound()) {
                highestScore = p;
            }
        }
        return highestScore;
    }
}
