package simongame;

import domain.Color;
import domain.GameMode;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonHighscoreGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;
import websockets.client.GameClientSocket;

public class SimonGameGUILogic implements ISimonGUI {

    private GameClientSocket gameClient;

    public SimonGameGUILogic(){
        gameClient = GameClientSocket.getInstance();
        gameClient.startClient();
    }

    @Override
    public void registerPlayer(String username, String mail, String password, ISimonRegisterGUI registerGUI) {
        if (username == null || mail == null || password == null) {
            throw new IllegalArgumentException();
        } else{
            gameClient.register(username, mail, password, registerGUI);
        }
    }

    @Override
    public void loginPlayer(String account, String password, ISimonSetupGUI setupGUI, boolean singlePlayerMode, GameMode gameMode) {
        if (account == null || password == null) {
            throw new IllegalArgumentException();
        } else{
            gameClient.loginPlayer(account, password, setupGUI, singlePlayerMode, gameMode);
        }
    }

    @Override
    public void openLobbyForPlayers(ISimonGameGUI gameGUI){
        gameClient.openLobby(gameGUI);
    }

    @Override
    public void setGameDifficulty(int difficulty){
        gameClient.setGameDifficulty(difficulty);
    }

    @Override
    public void notifyReady() {
        gameClient.notifyReady();
    }

    @Override
    public void startNewGame() {
        gameClient.startNewGame();
    }

    @Override
    public void beginShowingSequences(){
        gameClient.beginShowingSequences();
    }

    @Override
    public void quitGame(){
        gameClient.quitGame();
    }

    @Override
    public void enterSequence(Color color) {
        gameClient.enterSequence(color);
    }

    @Override
    public void repeatLastSequence() {
        gameClient.repeatLastSequece();
    }

    @Override
    public void getAllHighScores(ISimonHighscoreGUI highscoreGUI, GameMode filter){
        gameClient.getAllHighscores(highscoreGUI, filter);
    }

    @Override
    public void getHighestScore(GameMode gameMode){
        gameClient.getHighestScore(gameMode);
    }

}
