package websockets.messaging;

import javax.websocket.Session;

public interface IServerMessageProcessor {
    void processMessage(Session session, SimonGenericMessage msg);
}
