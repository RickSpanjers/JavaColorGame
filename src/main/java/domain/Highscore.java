package domain;

public class Highscore {
    private int difficulty;
    private int round;
    private String achieved;
    private GameMode gameMode;

    public Highscore(int difficulty, int round, GameMode gameMode) {
        this.difficulty = difficulty;
        this.round = round;
        this.gameMode = gameMode;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public String getAchieved() {
        return achieved;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
