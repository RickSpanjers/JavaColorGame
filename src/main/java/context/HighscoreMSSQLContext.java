package context;

import com.google.gson.Gson;
import domain.GameMode;
import domain.Highscore;
import domain.Player;
import helper.ConnectionHelper;
import logging.ILogger;
import logging.Logger;
import repository.IHighscore;
import repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HighscoreMSSQLContext implements IHighscore {

    private ConnectionHelper helper = new ConnectionHelper();
    private UserRepository userRepo = new UserRepository(new UserMSSQLContext());
    private ILogger logger = Logger.getInstance();

    public boolean updateHighScoreByUserId(int userId, Highscore h) {
        boolean result = false;
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement ps = conn.prepareStatement("UPDATE BigIdea.[Highscore] SET difficulty = ?, Round = ?, HighscoreAchieved = ? WHERE UserId = ? AND GameMode = ?");
            try{
                ps.setInt(1, h.getDifficulty());
                ps.setInt(2, h.getRound());
                ps.setString(3, LocalDateTime.now().toString());
                ps.setInt(4, userId);
                ps.setString(5, h.getGameMode().toString());
                ps.executeUpdate();
            }
            catch(Exception e){
                logger.log(e);
            }
            finally {
                ps.close();
                result = true;
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    public Highscore getHighScoreByUserId(int userId, GameMode filter) {
        Highscore h = new Highscore(0, 0, GameMode.CLASSIC);
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[Highscore] WHERE BigIdea.[Highscore].userId = ? AND BigIdea.[Highscore].GameMode = ?");
            myStmt.setInt(1, userId);
            myStmt.setString(2, filter.toString());
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                int difficulty = myResults.getInt("Difficulty");
                int round = myResults.getInt("Round");
                String achieved = myResults.getString("HighscoreAchieved");
                String gameMode = myResults.getString("GameMode");
                h = new Highscore(difficulty, round, getGameModeEnum(gameMode));
                h.setAchieved(achieved);
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return h;
    }

    public ArrayList<Player> getAllHighScores() {

        ArrayList<Player> listOfPlayers = new ArrayList<>();
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[Highscore]");
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                int userId = myResults.getInt("userId");
                int difficulty = myResults.getInt("Difficulty");
                int round = myResults.getInt("Round");
                String achieved = myResults.getString("HighscoreAchieved");
                String gameMode = myResults.getString("GameMode");
                Highscore h = new Highscore(difficulty, round, getGameModeEnum(gameMode));
                h.setAchieved(achieved);
                Player p = userRepo.getUserById(userId);
                p.setHighscore(h);
                listOfPlayers.add(p);
            }
        } catch (Exception e) {
            logger.log(e);
        }

        return listOfPlayers;
    }

    @Override
    public ArrayList<Player> getAllHighscoresByFilter(GameMode filter) {
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        try {
            Connection conn = helper.dbConnect();
            PreparedStatement myStmt = conn.prepareStatement("SELECT * FROM BigIdea.[Highscore] WHERE BigIdea.[Highscore].GameMode = ? ");
            myStmt.setString(1, filter.toString());
            ResultSet myResults = myStmt.executeQuery();

            while (myResults.next()) {
                int userId = myResults.getInt("userId");
                int difficulty = myResults.getInt("Difficulty");
                int round = myResults.getInt("Round");
                String achieved = myResults.getString("HighscoreAchieved");
                String gameMode = myResults.getString("GameMode");
                Highscore h = new Highscore(difficulty, round, getGameModeEnum(gameMode));
                h.setAchieved(achieved);
                Player p = userRepo.getUserById(userId);
                p.setHighscore(h);
                listOfPlayers.add(p);
            }
        } catch (Exception e) {
            logger.log(e);
        }

        return listOfPlayers;
    }

    public Player getHighestScore(GameMode filter) {
        Player result = new Player(0, null);
        List<Player> players = userRepo.getAllPlayers();

        for (Player p : players) {
            p.setHighscore(getHighScoreByUserId(p.getPlayerId(), filter));
        }
        for(Player p : players) {
            if (p.getHighscore().getDifficulty() > result.getHighscore().getDifficulty()) {
                result = p;
            }
            if (p.getHighscore().getDifficulty() == result.getHighscore().getDifficulty()) {
                if (p.getHighscore().getRound() > result.getHighscore().getRound()) {
                    result = p;
                }
            }
        }
        return result;
    }

    private GameMode getGameModeEnum(String gameMode){
        Gson gson = new Gson();
        GameMode gm = gson.fromJson(gameMode, GameMode.class);
        return gm;
    }
}
