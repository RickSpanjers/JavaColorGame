package simongame;

import domain.*;
import logging.ILogger;
import logging.Logger;
import rest.SimonRESTClient;
import simonalgorithm.ISequenceGenerator;
import simonalgorithm.SequenceGenerator;

public class SimonGameLogic implements ISimonGame {

    //Game and Algorithm
    private Game currentGame = new Game();
    private ISequenceGenerator gameAlgorithm;
    private boolean multiPlayer = false;
    private int currentPlayerStillPlaying;
    private SimonRESTClient restClient = new SimonRESTClient();
    private ILogger logger = Logger.getInstance();

    @Override
    public boolean registerPlayer(String username, String mail, String password) {
        boolean result = false;
        if (restClient.register(username, mail, password)) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean loginPlayer(String account, String password, boolean multiPlayer, int playerNr, GameMode gameMode) {
        boolean result = false;
        setGameMode(gameMode);
        Player p = restClient.login(account, password);
        try {
            if (p.getPlayerId() != 0) {
                result = true;
                p.setPlayerNr(playerNr);
                currentGame.addPlayer(p);
                if (multiPlayer) {
                    this.multiPlayer = true;
                }
            }
        } catch (NullPointerException e) {
            logger.log(e);
        }
        return result;
    }

    @Override
    public void setGameDifficulty(int difficulty) {
        if (currentGame.getDifficulty() == 0) {
            currentGame.setDifficulty(difficulty);
        }
    }

    @Override
    public boolean notifyReady(int playerNr) {
        boolean result = false;
        if (!multiPlayer) {
            result = true;
        } else {
            currentGame.getPlayerByPlayerNr(playerNr).getPlayerState().setReady(true);
            for(Player p: currentGame.getPlayers()){
                if(p.getPlayerState().getReady()){
                    result = true;
                } else{
                    return false;
                }
            }
        }
        return result;
    }

    @Override
    public void startNewGame() {
        gameAlgorithm = new SequenceGenerator(this);
        currentGame.setGameActive(true);
        currentGame.resetRound();
        currentGame.resetRoundFinished();
        resetGame();
        gameAlgorithm.initialize();
        gameAlgorithm.processRound();
        currentGame.updateRound();
        currentPlayerStillPlaying = currentGame.getPlayersThatHaveNotLost().size();
    }

    @Override
    public boolean enterSequence(Color color, int playerNr) {
        boolean result = false;
        int sequenceMaxSize = gameAlgorithm.getLastSequence().size();
        if (currentGame.getPlayerByPlayerNr(playerNr).getBoard().getPressedColors().size() < sequenceMaxSize) {
            currentGame.getPlayerByPlayerNr(playerNr).getBoard().addPressedColor(color);
        }
        if (currentGame.getPlayerByPlayerNr(playerNr).getBoard().getPressedColors().size() == sequenceMaxSize) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean processRoundResult(int playerNr) {
        boolean result = false;
        if (gameAlgorithm.validateSequence(currentGame.getPlayerByPlayerNr(playerNr).getBoard().getPressedColors())) {
            currentGame.getPlayerByPlayerNr(playerNr).getBoard().getPressedColors().clear();
            result = true;
        } else {
            currentGame.getPlayerByPlayerNr(playerNr).getPlayerState().setLost(true);
            currentGame.getPlayerByPlayerNr(playerNr).getBoard().getPressedColors().clear();
            Player p = currentGame.getPlayerByPlayerNr(playerNr);
            p.setHighscore(new Highscore(currentGame.getDifficulty(), currentGame.getRound(), currentGame.getGameMode()));
            restClient.setHighscore(p.getPlayerId(), p.getHighscore().getDifficulty(), p.getHighscore().getRound(), p.getHighscore().getGameMode());
        }
        currentGame.addPlayerCompletedRound();
        if(currentGame.getPlayersCompletedRound() == currentPlayerStillPlaying){
            if(isGameActive()){
                nextRound();
            } else{
                currentGame.setGameActive(false);
            }
        }
        return result;
    }

    @Override
    public void setGameVictor() {
        for (Player p : currentGame.getPlayers()) {
            p.setHighscore(new Highscore(currentGame.getDifficulty(), currentGame.getRound(), currentGame.getGameMode()));
            restClient.setHighscore(p.getPlayerId(), p.getHighscore().getDifficulty(), p.getHighscore().getRound(), p.getHighscore().getGameMode());
        }
    }

    @Override
    public String getAllHighScores(GameMode filter){
        return restClient.getAllHighscores(filter);
    }

    @Override
    public Player getRecordHolder(GameMode gameMode){
        return restClient.getHighestScore(gameMode);
    }

    @Override
    public Game getCurrentGame() {
        return currentGame;
    }

    @Override
    public boolean isMultiPlayer() {
        return multiPlayer;
    }

    @Override
    public ISequenceGenerator getGameAlgorithm() {
        return gameAlgorithm;
    }

    private void nextRound(){
        currentGame.resetRoundFinished();
        gameAlgorithm.processRound();
        currentGame.updateRound();
        currentGame.setRoundFinished(true);
        currentPlayerStillPlaying = currentGame.getPlayersThatHaveNotLost().size();
    }

    private boolean isGameActive(){
        boolean result = false;
        for(Player p: currentGame.getPlayers()){
            if(!p.getPlayerState().getLost()){
                result = true;
            }
        }
        return result;
    }

    private void resetGame(){
        for(Player p: currentGame.getPlayers()){
            p.getPlayerState().setLost(false);
        }
    }

    private boolean setGameMode(GameMode gameMode){
        boolean result = false;
        if(currentGame.getGameMode() == null){
            currentGame.setGameMode(gameMode);
            result = true;
        }else if(currentGame.getGameMode() != gameMode){
            result = false;
        }
        return result;
    }

}
