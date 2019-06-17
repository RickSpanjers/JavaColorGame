package websockets.server.messaging;

import com.google.gson.Gson;
import domain.Color;
import domain.GameMode;
import domain.Player;
import dto.LoginDTO;
import dto.RoundDTO;
import logging.ILogger;
import logging.Logger;
import simongame.ISimonGame;
import websockets.messaging.EnumOperationMessage;
import websockets.messaging.IServerMessageHandler;
import websockets.messaging.SimonGenericMessage;
import websockets.server.IServerSocket;

import javax.websocket.Session;

public class ServerGeneralMessageHandler implements IServerMessageHandler {

    private ISimonGame gameLogic;
    private Gson gson = new Gson();
    private IServerSocket serverSocket;
    private ILogger logger = Logger.getInstance();

    public ServerGeneralMessageHandler(ISimonGame game, IServerSocket serverSocket){
        this.gameLogic = game;
        this.serverSocket = serverSocket;
    }

    @Override
    public void handleMessage(EnumOperationMessage type, String message, Session session) {
        switch (type) {
            case REGISTER:
                registerPlayer(message, session);
                break;
            case LOGIN:
                loginPlayer(message, session);
                break;
            case SETOPPONENT:
                updateLobbyWhenNewPlayerJoins();
                break;
            case SETDIFFICULTY:
                gameLogic.setGameDifficulty(Integer.parseInt(message));
                break;
            case NOTIFYREADY:
                playerNotifiesTheyAreReady(session);
                break;
            case NOTIFYBEGINROUNDS:
                startGame(session);
                break;
            case ENTERSEQUENCE:
                enterSequence(message, session);
                break;
            case SHOWLASTSEQUENCE:
                sendLastSequenceForRepeat(session);
                break;
            case HIGHSCORES:
                getAllHighscores(session, message);
                break;
            case RECORDHOLDER:
                getRecordHolder(session, message);
                break;
            case STARTNEWGAME:
                notifyPlayAnotherGame();
                break;
            case QUITGAME:
                quitGame();
                break;
            default:
                break;
        }
    }

    private void registerPlayer(String data, Session session) {
        Player p = gson.fromJson(data, Player.class);
        SimonGenericMessage returnMsg = new SimonGenericMessage(EnumOperationMessage.REGISTERFAIL);
        if (gameLogic.registerPlayer(p.getPlayerName(), p.getPlayerMail(), p.getPlayerPassword())) {
            returnMsg = new SimonGenericMessage(EnumOperationMessage.REGISTERSUCCESS);
        }
        serverSocket.sendToClient(session, gson.toJson(returnMsg));
    }


    private void loginPlayer(String data, Session session) {
        LoginDTO dto = gson.fromJson(data, LoginDTO.class);
        if (allowPlayerToJoin(dto, session)) {
            SimonGenericMessage returnMsg = new SimonGenericMessage(EnumOperationMessage.LOGINFAIL);
            if (gameLogic.loginPlayer(dto.getUsername(), dto.getPassword(), dto.isMultiplayer(), serverSocket.getPlayerNumberSession(session), dto.getGameMode())) {
                returnMsg = new SimonGenericMessage(EnumOperationMessage.LOGINSUCCESS);
            }
            serverSocket.sendToClient(session, gson.toJson(returnMsg));
        }
    }

    private void updateLobbyWhenNewPlayerJoins() {
        SimonGenericMessage returnMsg = new SimonGenericMessage(EnumOperationMessage.SETOPPONENT);
        returnMsg.setData(gson.toJson(gameLogic.getCurrentGame().getPlayers()));
        serverSocket.broadcast(returnMsg);
    }

    private boolean allowPlayerToJoin(LoginDTO dto, Session session) {
        boolean result = true;
        try {
            if (!gameLogic.getCurrentGame().getPlayers().isEmpty() && gameLogic.isMultiPlayer() != dto.isMultiplayer()) {
                SimonGenericMessage returnMsg = new SimonGenericMessage(EnumOperationMessage.ERRORSETUP);
                returnMsg.setData("The system is currently occupied");
                serverSocket.sendToClient(session, gson.toJson(returnMsg));
                result = false;
            }
        } catch (Exception e) {
            logger.log(e);
        }
        return result;
    }

    private void playerNotifiesTheyAreReady(Session session) {
        SimonGenericMessage returnMsg = new SimonGenericMessage(EnumOperationMessage.NOTIFYWAIT);
        if (gameLogic.notifyReady(serverSocket.getPlayerNumberSession(session))) {
            gameLogic.startNewGame();
            SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.NOTIFYSTART);
            serverSocket.broadcast(msg);
        } else {
            serverSocket.sendToClient(session, gson.toJson(returnMsg));
        }
    }

    private void notifyPlayAnotherGame() {
        gameLogic.startNewGame();
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.NOTIFYSTART);
        serverSocket.broadcast(msg);
    }

    private void startGame(Session session) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.PROCESROUND);
        RoundDTO dto = new RoundDTO(gameLogic.getGameAlgorithm().getLastSequence(), gameLogic.getCurrentGame().getDifficulty());
        msg.setData(gson.toJson(dto));
        serverSocket.sendToClient(session, gson.toJson(msg));
    }


    private void enterSequence(String msg, Session session) {
        Color c = gson.fromJson(msg, Color.class);
        if (gameLogic.enterSequence(c, serverSocket.getPlayerNumberSession(session))) {
            validateSequence(session);
        }
    }

    private void validateSequence(Session session) {
        if (!gameLogic.processRoundResult(serverSocket.getPlayerNumberSession(session))) {

            SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.GAMELOSE);
            msg.setData(gson.toJson(serverSocket.getPlayerNumberSession(session)));
            serverSocket.sendToClient(session, gson.toJson(msg));

            msg = new SimonGenericMessage(EnumOperationMessage.OPPONENTLOSS);
            msg.setData(gson.toJson(serverSocket.getPlayerNumberSession(session)));
            serverSocket.sendToOthers(session.getId(), gson.toJson(msg));

            if (!gameLogic.getCurrentGame().getGameActive()) {
                msg = new SimonGenericMessage(EnumOperationMessage.GAMEFINISH);
                serverSocket.broadcast(msg);
            }
        }
        if (gameLogic.getCurrentGame().isRoundFinished()) {
            nextRound();
            gameLogic.getCurrentGame().setRoundFinished(false);
        }
    }

    private void nextRound() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.PROCESROUND);
        if (gameLogic.getCurrentGame().getRound() == 13) {
            msg = new SimonGenericMessage(EnumOperationMessage.GAMEFINISH);
            serverSocket.broadcast(msg);
        }
        for (Session s : serverSocket.getSessions()) {
            RoundDTO dto = new RoundDTO(gameLogic.getGameAlgorithm().getLastSequence(), gameLogic.getCurrentGame().getDifficulty());
            if (gameLogic.getCurrentGame().getPlayerByPlayerNr(serverSocket.getPlayerNumberSession(s)).getPlayerState().getLost()) {
                dto.setLost(true);
            }
            msg.setData(gson.toJson(dto));
           serverSocket.sendToClient(s, gson.toJson(msg));
        }
    }

    private void sendLastSequenceForRepeat(Session session) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.SHOWLASTSEQUENCE);
        RoundDTO dto = new RoundDTO(gameLogic.getGameAlgorithm().getLastSequence(), gameLogic.getCurrentGame().getDifficulty());
        msg.setData(gson.toJson(dto));
        serverSocket.sendToClient(session, gson.toJson(msg));
    }

    private void quitGame() {
        if (gameLogic.isMultiPlayer()) {
            SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.SYSTEMRESET);
            serverSocket.broadcast(msg);
        }
    }

    private void getAllHighscores(Session session, String data) {
        GameMode gm = gson.fromJson(data, GameMode.class);
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.HIGHSCORES);
        msg.setData(gameLogic.getAllHighScores(gm));
        serverSocket.sendToClient(session, gson.toJson(msg));
    }

    private void getRecordHolder(Session session, String data) {
        GameMode gm = gson.fromJson(data, GameMode.class);
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.RECORDHOLDER);
        msg.setData(gson.toJson(gameLogic.getRecordHolder(gm)));
        serverSocket.sendToClient(session, gson.toJson(msg));
    }
}
