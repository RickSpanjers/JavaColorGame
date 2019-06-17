package mocks;

import domain.Color;
import javafx.animation.FillTransition;
import simongame.ISimonGame;
import simongui.interfaces.ISimonGameGUI;

/**
 * Mock Simon application to support unit testing of the Simon Game
 * @author Rick Spanjres
 */
public class MockSimonGameGUI implements ISimonGameGUI {

    private ISimonGame game;
    private boolean playerMove = false;
    private FillTransition ft = new FillTransition();

    private boolean startGame = false;
    private boolean gameFinished = false;
    private Color color = null;
    private int speed = 0;
    private boolean haveToWait = false;
    private String opponentName;
    private int turn = 0;
    private String playerName;
    private int difficulty;
    private boolean playerLoss = false;
    private boolean playerCanEnter =false;
    private boolean shutdown = false;
    private String msg;
    private boolean nextRoundAnimationShown = false;


    /**
     * Show a message in Console
     * @param message the message that needs to be sent
     * @author Rick Spanjers
     */
    @Override
    public void showMessage(String message) {
        msg = message;
    }


    /**
     * Notify the start of the game
     * @author Rick Spanjers
     */
    @Override
    public void notifyStartGame() {
        startGame = true;
    }


    /**
     * Notify to wait until the other players are ready
     */
    @Override
    public void notifyWaitOthersMulti() {
        haveToWait = true;
    }


    /**
     * Set the game as finished
     * @author Rick Spanjers
     */
    @Override
    public void setGameFinished() {
        gameFinished = true;
    }


    /**
     * Start a new game for all players
     * @author Rick Spanjers
     */
    @Override
    public void startNewGame() {
        startGame = true;
    }


    /**
     * Show the square that a player must repeat
     * @param color is the color that the person must repeat
     * @param speed is the speed at which the color is shown
     * @author Rick Spanjers
     */
    @Override
    public void showSquareToPress(domain.Color color, int speed) {
        this.color = color;
        this.speed = speed;
    }


    /**
     * Set the loss of a player in the GUI
     * @param playerNr is the number of the player who lost
     * @author Rick Spanjers
     */
    @Override
    public void setPlayerLoss(int playerNr) {
        playerLoss = true;
    }


    /**
     * Increase the current turn
     * @author Rick Spanjers
     */
    @Override
    public void increaseRound() {
        turn++;
    }


    /**
     * Set the player as logged in and set the chosen difficulty
     * @param name is the name of the player
     * @param difficulty is the difficulty the player selected
     * @author Rick Spanjers
     */
    @Override
    public void setLoggedIn(String name, int difficulty) {
        playerName = name;
        this.difficulty = difficulty;
    }


    /**
     * Set the name of the opponent
     * @param opponentName opponentName
     * @param playerNr playerNr
     * @author Rick Spanjers
     */
    @Override
    public void setOpponentNames(String opponentName, int playerNr) {
        this.opponentName = opponentName;
    }


    /**
     * Allow player to enter sequences
     * @author Rick Spanjers
     */
    @Override
    public void allowPlayerToEnterSequence(boolean allow) {
        playerCanEnter = allow;
    }


    /**
     * Shutting down the game on for example another person leaving
     */
    @Override
    public void gameShutdown() {
        shutdown = true;
    }


    /**
     * Show next round animation
     */
    @Override
    public void showNextRoundAnimation() {
        nextRoundAnimationShown = true;
    }


    /**
     * Get the current turn
     * @return Integer Turn
     */
    public int getTurn() {
        return turn;
    }


    /**
     * Return the current difficulty
     * @return int difficulty (1-3)
     */
    public int getDifficulty() {
        return difficulty;
    }


    /**
     * Return the current speed
     * @return int speed
     */
    public int getSpeed() {
        return speed;
    }


    /**
     * get name of opponent
     * @return String opponentName
     */
    public String getOpponentName() {
        return opponentName;
    }


    /**
     * get the name of the player
     * @return String playerName
     */
    public String getPlayerName() {
        return playerName;
    }


    /**
     * get the FillTransition
     * @return JavaFx Filltransition ft
     */
    public FillTransition getFt() {
        return ft;
    }


    /**
     * get the color
     * @return Color color
     */
    public Color getColor() {
        return color;
    }


    /**
     * get if the player can enter a sequence
     * @return boolean playerMove
     */
    public boolean isPlayerMove() {
        return playerMove;
    }


    /**
     * Return if the game has started
     * @return boolean startGame
     */
    public boolean isStartGame(){
        return startGame;
    }

    /**
     * Return if the game has started or not
     * @return boolean gameFinished
     */
    public boolean isGameFinished() {
        return gameFinished;
    }


    /**
     * Return if player has to wait
     * @return boolean true or false
     */
    public boolean isHaveToWait() {
        return haveToWait;
    }

    /**
     * Return if player has lost
     * @return boolean true or false
     */
    public boolean isPlayerLoss() {
        return playerLoss;
    }

    /**
     * Return if game has shutdown or not
     * @return boolean true or false
     */
    public boolean isShutdown() {
        return shutdown;
    }

    /**
     * Return if the player can enter a sequence or not
     * @return boolean true or false
     */
    public boolean isPlayerCanEnter() {
        return playerCanEnter;
    }


    /**
     * Return last message given to the player
     * @return String message
     */
    public String getMsg(){
        return msg;
    }
}
