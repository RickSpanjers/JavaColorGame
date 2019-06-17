package websockets.messaging;

public interface IClientMessageProcessor {
    void processMessage(String sessionId, SimonGenericMessage msg);
    IClientMessageHandler getHandler();
}
