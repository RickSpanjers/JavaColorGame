package websockets.client;

import com.google.gson.Gson;
import domain.Color;
import domain.GameMode;
import logging.ILogger;
import logging.LogLevel;
import logging.Logger;
import simongui.interfaces.ISimonGameGUI;
import simongui.interfaces.ISimonHighscoreGUI;
import simongui.interfaces.ISimonRegisterGUI;
import simongui.interfaces.ISimonSetupGUI;
import websockets.client.messaging.ClientGeneralMessageHandler;
import websockets.client.messaging.ClientMessageGenerator;
import websockets.client.messaging.ClientMessageProcessor;
import websockets.messaging.IClientMessageGenerator;
import websockets.messaging.IClientMessageProcessor;
import websockets.messaging.SimonGenericMessage;
import javax.websocket.*;
import java.net.URI;


@ClientEndpoint
public class GameClientSocket implements IClientSocket {
    private Gson gson = new Gson();
    private Session session;
    private String serverUrl = "ws://localhost:8095/simongame/";
    private ILogger logger = Logger.getInstance();
    private IClientMessageProcessor processor = new ClientMessageProcessor(new ClientGeneralMessageHandler());
    private IClientMessageGenerator generator = new ClientMessageGenerator(this);

    private static GameClientSocket instance = null;

    public static synchronized GameClientSocket getInstance() {
        if (instance == null) {
            instance = new GameClientSocket();
        }
        return instance;
    }

    public void startClient() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(serverUrl));
        } catch (Exception ex) {
           logger.log(ex);
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Socket Connected: " + sess);
        this.session = sess;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session) {
        SimonGenericMessage msg = gson.fromJson(message, SimonGenericMessage.class);
        onWebSocketMessageReceived(msg, session);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        logger.log(cause.getMessage(), LogLevel.ERROR);
    }

    @Override
    public void onWebSocketMessageReceived(SimonGenericMessage msg, Session session) {
        processor.processMessage(session.getId(), msg);
    }

    @Override
    public void send(String json) {
        session.getAsyncRemote().sendText(json);
    }

    public void register(String username, String mail, String password, ISimonRegisterGUI registerGUI) {
        processor.getHandler().getGUINotify().setRegisterGUI(registerGUI);
        generator.register(username, mail, password);
    }

    public void loginPlayer(String account, String password, ISimonSetupGUI setupGUI, boolean singlePlayerMode, GameMode gameMode) {
        processor.getHandler().getGUINotify().setSetupGUI(setupGUI);
        generator.loginPlayer(account, password, singlePlayerMode, gameMode);
    }

    public void setGameDifficulty(int difficulty) {
        generator.setGameDifficulty(difficulty);
    }

    public void openLobby(ISimonGameGUI gameGUI){
        processor.getHandler().getGUINotify().setGameGUI(gameGUI);
        generator.openLobby();
    }

    public void notifyReady() {
        generator.notifyReady();
    }

    public void enterSequence(Color c) {
        generator.enterSequence(c);
    }

    public void startNewGame(){
        generator.startNewGame();
    }

    public void repeatLastSequece(){
        generator.repeatLastSequence();
    }

    public void quitGame(){
        generator.quitGame();
    }

    public void getAllHighscores(ISimonHighscoreGUI highscoreGUI, GameMode filter){
        processor.getHandler().getGUINotify().setHighScoreGUI(highscoreGUI);
        generator.getAllHighScores(filter);
    }

    public void getHighestScore(GameMode gameMode){
        generator.getHighestScore(gameMode);
    }

    public void beginShowingSequences(){
        generator.beginShowingSequences();
    }



}
