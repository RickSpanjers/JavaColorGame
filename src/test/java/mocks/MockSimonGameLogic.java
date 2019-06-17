package mocks;

import domain.Color;
import domain.Game;
import domain.GameMode;
import domain.Player;
import simonalgorithm.ISequenceGenerator;
import simongame.ISimonGame;

public class MockSimonGameLogic implements ISimonGame {

    private int difficulty = 0;
    private boolean started = false;
    private Game game = new Game();
    private boolean multiplayer = false;

    @Override
    public boolean registerPlayer(String username, String mail, String password) {
        return true;
    }

    @Override
    public boolean loginPlayer(String account, String password, boolean multiplayer, int playerNr, GameMode gameMode) {
        return true;
    }

    @Override
    public void setGameDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean notifyReady(int playerNr) {
        return true;
    }

    @Override
    public void startNewGame() {
        started = true;
    }

    @Override
    public boolean enterSequence(Color color, int playerNr) {
       return true;
    }

    @Override
    public boolean processRoundResult(int playerNr) {
        return true;
    }

    @Override
    public void setGameVictor() {
        //Not implemented
    }

    @Override
    public String getAllHighScores(GameMode filter) {
        return null;
    }

    @Override
    public Player getRecordHolder(GameMode gameMode) {
        return new Player(1, "test");
    }

    @Override
    public Game getCurrentGame() {
        return game;
    }

    @Override
    public boolean isMultiPlayer() {
        return multiplayer;
    }

    @Override
    public ISequenceGenerator getGameAlgorithm() {
        return null;
    }
}
