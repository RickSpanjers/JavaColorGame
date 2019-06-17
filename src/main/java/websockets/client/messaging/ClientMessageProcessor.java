package websockets.client.messaging;

import simongame.SimonGameGUILogic;
import websockets.messaging.IClientMessageHandler;
import websockets.messaging.IClientMessageProcessor;
import websockets.messaging.SimonGenericMessage;

public class ClientMessageProcessor implements IClientMessageProcessor {

    private SimonGameGUILogic guiLogic;
    private IClientMessageHandler handler;

    public ClientMessageProcessor(IClientMessageHandler messageHandler){
        handler = messageHandler;
    }

    public void setGameGUI(SimonGameGUILogic guiLogic)
    {
        this.guiLogic = guiLogic;
    }

    public SimonGameGUILogic getGuiLogic()
    {
        return guiLogic;
    }

    @Override
    public void processMessage(String sessionId, SimonGenericMessage msg) {
        handler.handleMessage(msg.getOperationMessage(), msg.getData(), sessionId);
    }

    @Override
    public IClientMessageHandler getHandler() {
        return handler;
    }
}
