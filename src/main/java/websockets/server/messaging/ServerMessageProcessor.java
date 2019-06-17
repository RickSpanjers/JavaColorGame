package websockets.server.messaging;

import simongame.SimonGameLogic;
import websockets.messaging.IServerMessageHandler;
import websockets.messaging.IServerMessageProcessor;
import websockets.messaging.SimonGenericMessage;

import javax.websocket.Session;

public class ServerMessageProcessor implements IServerMessageProcessor {

    private SimonGameLogic gameLogic;
    private IServerMessageHandler handler;

    public ServerMessageProcessor(IServerMessageHandler messageHandler)
    {
        handler = messageHandler;
    }

    public void setGameLogic(SimonGameLogic game)
    {
        this.gameLogic = game;
    }

    public SimonGameLogic getGame()
    {
        return gameLogic;
    }

    @Override
    public void processMessage(Session session, SimonGenericMessage msg) {
        handler.handleMessage(msg.getOperationMessage(), msg.getData(), session);
    }
}
