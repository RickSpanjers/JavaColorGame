package domain;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private int difficulty;
    private int currentRound;
    private int playersCompletedRound;
    private boolean roundFinished;
    private boolean gameActive;
    private GameMode gameMode;

    public Game() {
        roundFinished = false;
        gameActive = false;
        playersCompletedRound = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByPlayerNr(int playerNr){
        Player result = new Player(0, null);
        for(Player p : players){
            if(p.getPlayerNr() == playerNr){
                result = p;
            }
        }
        return result;
    }

    public ArrayList<Player> getPlayersThatHaveNotLost(){
        ArrayList<Player> playersNotLost = new ArrayList<>();
        for(Player p : players){
            if(!p.getPlayerState().getLost()){
                playersNotLost.add(p);
            }
        }
        return playersNotLost;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void updateRound() {
        currentRound++;
    }

    public int getRound() {
        return currentRound;
    }

    public void resetRound() {
        currentRound = 0;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPlayersCompletedRound() {
        return playersCompletedRound;
    }

    public void addPlayerCompletedRound(){
        playersCompletedRound++;
    }

    public void resetRoundFinished(){
        playersCompletedRound = 0;
    }

    public boolean getGameActive(){
        return gameActive;
    }

    public void setGameActive(boolean active){
        gameActive = active;
    }

    public void setRoundFinished(boolean roundFinished) {
        this.roundFinished = roundFinished;
    }

    public boolean isRoundFinished() {
        return roundFinished;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}

