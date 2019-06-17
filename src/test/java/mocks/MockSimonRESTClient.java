package mocks;

import domain.GameMode;
import domain.Highscore;
import domain.Player;
import rest.IRESTClient;

public class MockSimonRESTClient implements IRESTClient {

    /**
     * Mock the login with REST
     * @param username String username
     * @param password String password
     * @return Player Object
     */
    @Override
    public Player login(String username, String password) {
        return new Player(1, "Test");
    }


    /**
     * Mock the registration with REST
     * @param username String
     * @param mail String
     * @param password String
     * @return boolean true or false
     */
    @Override
    public boolean register(String username, String mail, String password) {
        return true;
    }


    /**
     * Get Highscore of player via REST
     * @param playerId INT
     * @return Highscore Object
     */
    @Override
    public Highscore getHighscore(int playerId) {
        return new Highscore(1, 1, GameMode.CLASSIC);
    }


    /**
     * Set the highscore of a player via REST
     * @param playerId INT
     * @param difficulty INT
     * @param turn INT
     * @param mode GameMode ENUM
     * @return Boolean true or false
     */
    @Override
    public boolean setHighscore(int playerId, int difficulty, int turn, GameMode mode) {
        return true;
    }


    /**
     * Return the highest score of a particular gamemode
     * @param gameMode GameMode Enum
     * @return Player object
     */
    @Override
    public Player getHighestScore(GameMode gameMode) {
        return new Player(1, "Test");
    }


    /**
     * Return all highscores for a specific gamemode
     * @param filter GameMode Enum
     * @return String data
     */
    @Override
    public String getAllHighscores(GameMode filter) {
        return "Success";
    }
}
