package dto.restDTO;

import domain.GameMode;

public class SetHighscoreDTO {
    private int playerId;
    private int turn;
    private int difficulty;
    private GameMode gameMode;

    public SetHighscoreDTO(){

    }

    public SetHighscoreDTO(int playerId, int turn, int difficulty, GameMode gameMode){
        this.playerId = playerId;
        this.turn = turn;
        this.gameMode = gameMode;
        this.difficulty = difficulty;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
