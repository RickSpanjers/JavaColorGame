package websockets.client.notify;

import domain.Color;
import domain.Player;
import logging.ILogger;
import logging.Logger;
import simonalgorithm.ShowColorThread;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonHighscoreGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;
import java.util.ArrayList;

public class NotifyGUI implements IClientNotify {

    private ISimonSetupGUI setupGUI;
    private ISimonGameGUI gameGUI;
    private ISimonHighscoreGUI highScoreGUI;
    private ISimonRegisterGUI registerGUI;
    private Thread showColorsGUIThread;

    @Override
    public void setRegisterGUI(ISimonRegisterGUI registerGUI) {
        this.registerGUI = registerGUI;
    }

    @Override
    public void setSetupGUI(ISimonSetupGUI setupGUI) {
        this.setupGUI = setupGUI;
    }

    @Override
    public void setGameGUI(ISimonGameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    @Override
    public void setHighScoreGUI(ISimonHighscoreGUI highscoreGUI){this.highScoreGUI = highscoreGUI;}

    @Override
    public void notifyRegistrationSuccess() {
        registerGUI.showMessage("Successfully registered");
        registerGUI.completeRegistration();
    }

    @Override
    public void notifyRegistrationFailure() {
        registerGUI.showMessage("Registration failed! " + System.lineSeparator() + "The username and/or mailaddress might already be taken." + System.lineSeparator() + "The password might not contain an uppercase letter, number or special character");
    }

    @Override
    public void notifyLoginSuccess() {
        setupGUI.isLoggedIn(true);
        setupGUI.switchAfterSuccessfulLogin();
        setupGUI.loginButtonActive(false);
    }

    @Override
    public void notifyLoginFailure() {
        setupGUI.showMessage("Did you use the correct credentials?", "None");
        setupGUI.isLoggedIn(false);
        setupGUI.loginButtonActive(false);
    }

    @Override
    public void notifySetOpponents(ArrayList<Player> otherPlayers) {
        for(Player p: otherPlayers){
            gameGUI.setOpponentNames(p.getPlayerName(), p.getPlayerNr());
        }
    }

    @Override
    public void notifyOpponentLoss(int playerNr) {
        gameGUI.setPlayerLoss(playerNr);
    }

    @Override
    public void notifyStartGame() {
        gameGUI.startNewGame();
        gameGUI.notifyStartGame();
        gameGUI.showMessage("Repeat the sequences!" + System.lineSeparator() + "Press 'Start Showing Sequences' to begin!");
    }

    @Override
    public void notifyClientWait() {
        gameGUI.showMessage("Wait until the other players are also ready!");
        gameGUI.notifyWaitOthersMulti();
    }

    @Override
    public void notifyErrorMsgSetup(String error) {
        setupGUI.showMessage(error, "None");
    }

    @Override
    public void notifyErrorMsgGame(String error) {
        gameGUI.showMessage(error);
    }

    @Override
    public void notifyLastSequence(ArrayList<Color> lastSequence, int difficulty) {
        showColorsGUIThread = new Thread(new ShowColorThread(gameGUI, lastSequence, difficulty, true, 0));
        showColorsGUIThread.start();
    }

    @Override
    public void notifyGameFinish() {
        gameGUI.showMessage("Game finished!");
        gameGUI.setGameFinished();
    }

    @Override
    public void notifyRound(ArrayList<Color> sequenceToSend, int difficulty, boolean lost) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        gameGUI.increaseRound();
        gameGUI.allowPlayerToEnterSequence(false);
        showColorsGUIThread = new Thread(new ShowColorThread(gameGUI, sequenceToSend, difficulty, lost, 800));
        showColorsGUIThread.start();
    }

    @Override
    public void notifyGameLose(int playerNr) {
        gameGUI.allowPlayerToEnterSequence(false);
        gameGUI.setPlayerLoss(playerNr);
        gameGUI.setGameFinished();
        gameGUI.showMessage("Your sequence of colors was incorrect! GAME OVER!");
    }

    @Override
    public void notifySystemReset() {
        gameGUI.showMessage("Someone left the game... Closing system");
        gameGUI.gameShutdown();
    }

    @Override
    public void notifyAllHighScores(ArrayList<Player> highScores) {
        highScoreGUI.setHighScoresForTable(highScores);
    }

    @Override
    public void notifyRecordHolder(Player p) {
        highScoreGUI.showMessage("The current recordholder is " + p.getPlayerName() + System.lineSeparator() + "Difficulty " + p.getHighscore().getDifficulty() + System.lineSeparator() + "Round " + p.getHighscore().getRound() + System.lineSeparator() + "Achieved " + p.getHighscore().getAchieved());
    }
}
