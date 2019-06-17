package websockets.messaging;

import websockets.client.notify.IClientNotify;

public interface IClientMessageHandler {
    void handleMessage(EnumOperationMessage type, String message, String sessionId);
    IClientNotify getGUINotify();
    void setGUINotify(IClientNotify notify);
}
