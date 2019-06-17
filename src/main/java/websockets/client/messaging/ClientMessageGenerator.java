package websockets.client.messaging;

import com.google.gson.Gson;
import domain.Color;
import domain.GameMode;
import domain.Player;
import dto.LoginDTO;
import websockets.client.IClientSocket;
import websockets.messaging.EnumOperationMessage;
import websockets.messaging.IClientMessageGenerator;
import websockets.messaging.SimonGenericMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {

    private IClientSocket clientSocket;
    private Gson gson = new Gson();

    public ClientMessageGenerator(IClientSocket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void register(String username, String mail, String password) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.REGISTER);
        Player p = new Player(0, username, mail, password);
        msg.setData(gson.toJson(p));
        String json = gson.toJson(msg);
        clientSocket.send(json);
    }

    @Override
    public void loginPlayer(String account, String password, boolean singlePlayerMode, GameMode gameMode) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.LOGIN);
        LoginDTO loginDTO = new LoginDTO(account, account, password, singlePlayerMode, gameMode);
        msg.setData(gson.toJson(loginDTO));
        String json = gson.toJson(msg);
        clientSocket.send(json);
    }

    @Override
    public void setGameDifficulty(int difficulty) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.SETDIFFICULTY);
        msg.setData(gson.toJson(difficulty));
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void openLobby() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.SETOPPONENT);
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void notifyReady() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.NOTIFYREADY);
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void enterSequence(Color c) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.ENTERSEQUENCE);
        msg.setData(gson.toJson(c));
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void startNewGame() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.STARTNEWGAME);
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void repeatLastSequence() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.SHOWLASTSEQUENCE);
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void quitGame() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.QUITGAME);
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void getAllHighScores(GameMode filter) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.HIGHSCORES);
        msg.setData(gson.toJson(filter));
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void getHighestScore(GameMode gameMode) {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.RECORDHOLDER);
        msg.setData(gson.toJson(gameMode));
        clientSocket.send(gson.toJson(msg));
    }

    @Override
    public void beginShowingSequences() {
        SimonGenericMessage msg = new SimonGenericMessage(EnumOperationMessage.NOTIFYBEGINROUNDS);
        clientSocket.send(gson.toJson(msg));
    }
}
