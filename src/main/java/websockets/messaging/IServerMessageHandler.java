package websockets.messaging;

import javax.websocket.Session;

public interface IServerMessageHandler {
    void handleMessage(EnumOperationMessage type, String message, Session session);
}
