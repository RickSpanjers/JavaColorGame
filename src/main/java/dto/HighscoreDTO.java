package dto;

public class HighscoreDTO {
    private String playerName;
    private int round;
    private int difficulty;
    private String achieved;

    public HighscoreDTO(String playerName, int round, int difficulty, String achieved){
        this.playerName = playerName;
        this.round = round;
        this.difficulty = difficulty;
        this.achieved = achieved;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
