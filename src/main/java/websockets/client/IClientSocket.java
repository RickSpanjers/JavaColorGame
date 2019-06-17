package websockets.client;

import websockets.messaging.SimonGenericMessage;

import javax.websocket.Session;

public interface IClientSocket {
    void onWebSocketMessageReceived(SimonGenericMessage msg, Session session);
    void send(String json);
}
