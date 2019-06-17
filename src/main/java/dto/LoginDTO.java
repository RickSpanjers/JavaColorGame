package dto;

import domain.GameMode;

public class LoginDTO {
    private String username;
    private String password;
    private String mail;
    private boolean multiplayer;
    private GameMode gameMode;

    public LoginDTO(String username, String mail, String password, boolean multiplayermode, GameMode gameMode){
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.multiplayer = multiplayermode;
        this.gameMode = gameMode;
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
    }
}
