package websockets.server;

import websockets.messaging.SimonGenericMessage;

import javax.websocket.Session;
import java.util.ArrayList;

public interface IServerSocket {
    void broadcast(SimonGenericMessage msg);
    void sendTo(String sessionId, Object object);
    void sendToOthers(String sessionId, Object object);
    Session getSessionFromId(String sessionId);
    void sendToClient(Session session, String message);
    int getPlayerNumberSession(Session session);
    ArrayList<Session> getSessions();
}
