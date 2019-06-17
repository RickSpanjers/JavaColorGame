package domain;

public class Player {

    private int playerId;
    private int playerNr;
    private String playerName;
    private String playerMail;
    private String playerPassword;
    private Highscore highscore;
    private PlayerState playerState = new PlayerState();
    private Board board = new Board();

    public Player(int playerId, String username, String mail, String password) {
        this.playerId = playerId;
        playerName = username;
        playerMail = mail;
        playerPassword = password;
        highscore = new Highscore(0, 0, GameMode.CLASSIC);
    }

    public Player(int playerNr, String username) {
        this.playerNr = playerNr;
        playerName = username;
        playerMail = username;
        highscore = new Highscore(0, 0, GameMode.CLASSIC);
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playernr) {
        playerNr = playernr;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playername) {
        playerName = playername;
    }

    public String getPlayerMail() {
        return playerMail;
    }

    public void setPlayerMail(String playerMail) {
        this.playerMail = playerMail;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public void setHighscore(Highscore highscore) {
        this.highscore = highscore;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
